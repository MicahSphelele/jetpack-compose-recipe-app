package com.recipeapp.domain.model.events

sealed class RecipeListDataEvent {
    data object SearchEvent: RecipeListDataEvent()

    data object NextPageEvent: RecipeListDataEvent()

    data object RestoreStateEvent : RecipeListDataEvent()
}
