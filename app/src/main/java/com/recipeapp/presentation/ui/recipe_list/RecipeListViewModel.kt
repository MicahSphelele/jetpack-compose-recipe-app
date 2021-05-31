package com.recipeapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeapp.domain.model.ErrorState
import com.recipeapp.domain.model.Recipe
import com.recipeapp.network.NetworkServiceBuilder
import com.recipeapp.repository.RecipeRepository
import com.recipeapp.util.AppLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

class RecipeListViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository,
    @Named(NetworkServiceBuilder.NAMED_TOKEN)
    private val token: String
) : ViewModel() {

    object RecipeListViewModelConstants {
        const val PAGE_SIZE = 30
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
        search()
    }

    fun onQueryChange(query: String) {
        this.query.value = query
    }

    fun search() {
        viewModelScope.launch {

            loading.value = true
            resetSearchState()
            delay(2500)

            try {
                loading.value = false
                recipes.value = repository.search(token, 1, query.value)
            } catch (ex: Exception) {
                ex.printStackTrace()
                errorState.value = ErrorState(true, ex.message)
            }
        }
    }

    fun onSelectedCategoryChange(category: String) {

        val foodCategory = getFoodCategory(category)
        selectedFoodCategory.value = foodCategory
        onQueryChange(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    fun onChangeRecipeListScrollPosition(position: Int) {
        recipeListScrollPosition  = position
    }

    fun nextPage() {
        viewModelScope.launch {
            //Prevent duplicate events due to recompose happening too quickly
            if ((recipeListScrollPosition + 1) >= (page.value * RecipeListViewModelConstants.PAGE_SIZE)) {
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
    }


    private fun clearSelectedCategory() {
        selectedFoodCategory.value = null
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeListScrollPosition(0)
        if (selectedFoodCategory.value?.value != query.value){
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
        page.value = page.value + 1
    }



}