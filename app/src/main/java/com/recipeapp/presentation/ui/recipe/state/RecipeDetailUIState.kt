package com.recipeapp.presentation.ui.recipe.state

import com.recipeapp.domain.model.ErrorState
import com.recipeapp.domain.model.Recipe

data class RecipeDetailUIState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val errorState: ErrorState = ErrorState(false, null)
)
