package com.recipeapp.domain.model.events

sealed class RecipeListEvent {
    object SearchEvent: RecipeListEvent()
    object NextPageEvent: RecipeListEvent()
    //Restore after process death
    object RestoreStateEvent : RecipeListEvent()
}
