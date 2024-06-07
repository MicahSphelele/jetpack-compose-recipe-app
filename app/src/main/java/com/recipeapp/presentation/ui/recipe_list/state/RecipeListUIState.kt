package com.recipeapp.presentation.ui.recipe_list.state

import com.recipeapp.domain.model.ErrorState
import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.model.enums.FoodCategory

data class RecipeListUIState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val query: String = "",
    val selectedCategory: FoodCategory? = null,
    val page: Int = 1,
    val errorState: ErrorState = ErrorState()
)
