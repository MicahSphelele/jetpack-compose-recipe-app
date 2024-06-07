package com.recipeapp.presentation.ui.recipe.event

sealed class RecipeDetailEvent {

    data object OnStart: RecipeDetailEvent()

    data object OnBackPressed: RecipeDetailEvent()
}