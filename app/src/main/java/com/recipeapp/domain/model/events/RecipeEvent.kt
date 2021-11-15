package com.recipeapp.domain.model.events

sealed class RecipeEvent {

    data class GetDetailedRecipeEvent(val id : Int) : RecipeEvent()
}