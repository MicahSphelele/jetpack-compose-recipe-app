package com.recipeapp.presentation.ui.recipe_list

sealed class RecipeListEvent {
    object SearchEvent: RecipeListEvent()
    object NextPageEvent: RecipeListEvent()
}
