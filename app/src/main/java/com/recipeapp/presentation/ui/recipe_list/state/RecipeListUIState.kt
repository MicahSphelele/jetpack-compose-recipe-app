package com.recipeapp.presentation.ui.recipe_list.state

import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.model.enums.FoodCategory

data class RecipeListUIState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val query: String = "",
    val selectedFoodCategory: FoodCategory? = null,
    val page: Int = 1,
)
