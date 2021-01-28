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
import kotlinx.coroutines.launch
import javax.inject.Named

class RecipeListViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository,
    @Named(NetworkServiceBuilder.NAMED_TOKEN)
    private val token: String
) : ViewModel() {
    companion object {
        private const val TAG = "RecipeListViewModel"
    }

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val errorState: MutableState<ErrorState> = mutableStateOf(ErrorState(false, null))
    val query = mutableStateOf("")
    val selectedFoodCategory : MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition = 0f


    init {
        search()
    }

    fun onQueryChange(query: String) {
        this.query.value = query
    }

    fun search() {
        viewModelScope.launch {
            try {
                recipes.value = repository.search(token, 1, query.value)
            } catch (ex: Exception) {
                ex.printStackTrace()
                errorState.value = ErrorState(true, ex.message)
            }
        }
    }

    fun onSelectedCategoryChange(category: String){

        val foodCategory = getFoodCategory(category)
        selectedFoodCategory.value = foodCategory
        onQueryChange(category)
    }

    fun onChangeCategoryScrollPosition(posistion: Float){
        categoryScrollPosition = posistion
    }


}