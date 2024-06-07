package com.recipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeapp.data.network.NetworkServiceBuilder
import com.recipeapp.domain.model.ErrorState
import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.model.enums.FoodCategory
import com.recipeapp.domain.model.enums.getFoodCategory
import com.recipeapp.domain.model.events.RecipeListDataEvent
import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.presentation.ui.recipe_list.state.RecipeListUIState
import com.recipeapp.util.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    companion object {
        const val PAGE_SIZE = 30
        const val STATE_KEY_PAGE = "recipe.state.page.key"
        const val STATE_KEY_QUERY = "recipe.state.query.key"
        const val STATE_LIST_POSITION = "recipe.state.query.list_position"
        const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"
    }

    private val _uiState = MutableStateFlow(RecipeListUIState())
    val uiState = _uiState.asStateFlow()

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
            onTriggeredEvent(RecipeListDataEvent.RestoreStateEvent)
        } else {
            onTriggeredEvent(RecipeListDataEvent.SearchEvent)
        }
    }

    fun onQueryChange(query: String) {
        setQuery(query)
    }

    fun onTriggeredEvent(event: RecipeListDataEvent) {
        AppLogger.info("$event")
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListDataEvent.SearchEvent -> {
                        search()
                    }

                    is RecipeListDataEvent.NextPageEvent -> {
                        nextPage()
                    }

                    is RecipeListDataEvent.RestoreStateEvent -> {
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
        setSelectedCategory(foodCategory)
        onQueryChange(category)
        onTriggeredEvent(RecipeListDataEvent.SearchEvent)
    }

    fun onChangeRecipeListScrollPosition(position: Int) {
        setListScrollPosition(position)
    }

    fun updateShowDialogState(isDialogShowing: Boolean) {
        _uiState.value = uiState.value.copy(isDialogShowing = isDialogShowing)
    }

    private suspend fun search() {

        updateIsLoading(isLoading = true)
        resetSearchState()
        delay(1000)

        try {
            val recipes = repository.search(token, _uiState.value.page, _uiState.value.query)
            updateRecipes(recipes = recipes)
            updateIsLoading(isLoading = false)
        } catch (ex: Exception) {
            ex.printStackTrace()
            AppLogger.error("Something went wrong on the server : ${ex.message}")
            updateIsLoading(isLoading = false)
            updateShowDialogState(isDialogShowing = true)
            updateErrorState(errorState = ErrorState(true, ex.message))
        }
    }

    private suspend fun nextPage() {
        //Prevent duplicate events due to recompose happening too quickly
        if ((recipeListScrollPosition + 1) >= (_uiState.value.page * PAGE_SIZE)) {
            updateIsLoading(isLoading = true)
            incrementPage()
            AppLogger.info("Next Page triggered : ${_uiState.value.page}")
            delay(1000)
            if (_uiState.value.page > 1) {
                val results = repository.search(
                    token = token,
                    page = _uiState.value.page,
                    query = _uiState.value.query
                )
                AppLogger.info("Results : $results")
                appendRecipes(results)
            }
            updateIsLoading(isLoading = false)
        }

    }

    private suspend fun restoreAppState() {
        updateIsLoading(isLoading = true)
        val results: MutableList<Recipe> = mutableListOf()
        updateShowDialogState(isDialogShowing = false)
        updateErrorState(errorState = ErrorState(false, null))

        for (p in 1.._uiState.value.page) {

            if (!_uiState.value.errorState.hasError) {

                try {
                    val result =
                        repository.search(token = token, page = p, query = _uiState.value.query)
                    results.addAll(result)
                    if (p == _uiState.value.page) {
                        updateRecipes(recipes = results)
                        updateIsLoading(isLoading = false)
                    }
                } catch (ex: Exception) {
                    AppLogger.error("Something went wrong on the server : ${ex.message}")
                    updateShowDialogState(isDialogShowing = true)
                    updateErrorState(
                        errorState = ErrorState(
                            hasError = true,
                            errorMessage = "Failed to restore app state"
                        )
                    )
                }

            }
        }
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    private fun resetSearchState() {
        updateRecipes(recipes = listOf())
        updatePage(page = 1)
        onChangeRecipeListScrollPosition(0)
        if (_uiState.value.selectedCategory?.value != _uiState.value.query) {
            clearSelectedCategory()
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */

    private fun appendRecipes(newRecipes: List<Recipe>) {
        val current = ArrayList(_uiState.value.recipes)
        current.addAll(newRecipes)
        updateRecipes(recipes = current)
    }

    private fun incrementPage() {
        setPage(_uiState.value.page + 1)
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle[STATE_LIST_POSITION] = position
    }

    private fun setPage(page: Int) {
        updatePage(page = page)
        savedStateHandle[STATE_KEY_PAGE] = page
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        updateFoodCategory(selectedCategory = category)
        savedStateHandle[STATE_KEY_SELECTED_CATEGORY] = category
    }

    private fun setQuery(query: String) {
        updateQuery(query = query)
        savedStateHandle[STATE_KEY_QUERY] = query
    }

    private fun updateRecipes(recipes: List<Recipe>) {
        _uiState.value = uiState.value.copy(recipes = recipes)
    }

    private fun updateErrorState(errorState: ErrorState) {
        _uiState.value = uiState.value.copy(errorState = errorState)
    }

    private fun updateQuery(query: String) {
        _uiState.value = uiState.value.copy(query = query)
    }

    private fun updateFoodCategory(selectedCategory: FoodCategory?) {
        _uiState.value = uiState.value.copy(selectedCategory = selectedCategory)
    }

    private fun updatePage(page: Int) {
        _uiState.value = uiState.value.copy(page = page)
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _uiState.value = uiState.value.copy(isLoading = isLoading)
    }
}