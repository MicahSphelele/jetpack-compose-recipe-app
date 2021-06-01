package com.recipeapp.presentation.ui.recipe

sealed class RecipeEvent {

    data class GetDetailedRecipeEvent(val id : Int) : RecipeEvent()
}