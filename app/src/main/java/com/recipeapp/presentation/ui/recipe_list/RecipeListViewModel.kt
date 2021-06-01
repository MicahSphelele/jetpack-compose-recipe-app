package com.recipeapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeapp.domain.model.ErrorState
import com.recipeapp.domain.model.FoodCategory
import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.model.getFoodCategory
import com.recipeapp.network.NetworkServiceBuilder
import com.recipeapp.repository.RecipeRepository
import com.recipeapp.util.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named(NetworkServiceBuilder.NAMED_TOKEN)
    private val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object  {
        const val PAGE_SIZE = 30
        const val STATE_KEY_PAGE = "recipe.state.page.key"
        const val STATE_KEY_QUERY = "recipe.state.query.key"
        const val STATE_LIST_POSITION = "recipe.state.query.list_position"
        const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"
    }

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val errorState: MutableState<ErrorState> = mutableStateOf(ErrorState(false, null))
    val query = mutableStateOf("")
    val selectedFoodCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition = 0f
    var loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { savedPage ->
            setPage(savedPage)
        }

        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { savedQuery ->
            setQuery(savedQuery)
        }
        savedStateHandle.get<Int>(STATE_LIST_POSITION)?.let { savedPosition ->
            setListScrollPosition(savedPosition)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { savedCategory ->
            setSelectedCategory(savedCategory)
        }

        if (recipeListScrollPosition != 0) {
            AppLogger.info("Restoring app state")
            onTriggeredEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggeredEvent(RecipeListEvent.SearchEvent)
        }
    }

    fun onQueryChange(query: String) {
        setQuery(query)
    }

    fun onTriggeredEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.SearchEvent -> {
                        search()
                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }

                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreAppState()
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                AppLogger.error(ex)
            }
        }
    }


    fun onSelectedCategoryChange(category: String) {
        val foodCategory = getFoodCategory(category)
        //selectedFoodCategory.value = foodCategory
        setSelectedCategory(foodCategory)
        onQueryChange(category)
        onTriggeredEvent(RecipeListEvent.SearchEvent)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    fun onChangeRecipeListScrollPosition(position: Int) {
        setListScrollPosition(position)
    }

    private suspend fun search() {
        AppLogger.info("Start querying data : ${query.value}")
        loading.value = true
        resetSearchState()
        delay(2500)

        try {
            loading.value = false
            recipes.value = repository.search(token, page.value, query.value)
        } catch (ex: Exception) {
            ex.printStackTrace()
            AppLogger.error("Something went wrong on the server : ${ex.message}")
            errorState.value = ErrorState(true, ex.message)
        }
    }

    private suspend fun nextPage() {
        //Prevent duplicate events due to recompose happening too quickly
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            AppLogger.info("Next Page triggered : ${page.value}")
            delay(1000)
            if (page.value > 1) {
                val results = repository.search(token, page.value, query.value)
                AppLogger.info("Results : $results")
                appendRecipes(results)
            }
            loading.value = false
        }

    }

    private suspend fun restoreAppState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        errorState.value = ErrorState(false, null)

        for (p in 1..page.value) {

            if (!errorState.value.hasError) {

                try {
                    val result = repository.search(token = token, page = p, query = query.value)
                    results.addAll(result)
                    if (p == page.value) {
                        recipes.value = results
                        loading.value = false
                    }
                } catch (ex: Exception) {
                    AppLogger.error("Something went wrong on the server : ${ex.message}")
                    errorState.value = ErrorState(true, "Failed to restore app state")
                }

            }
        }
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeListScrollPosition(0)
        if (selectedFoodCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */

    private fun appendRecipes(newRecipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(newRecipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedFoodCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }


}