package com.recipeapp.presentation.ui.recipe_list.event

import com.recipeapp.domain.model.enums.ThemeState
import com.recipeapp.domain.model.events.RecipeListDataEvent
import com.recipeapp.presentation.ui.recipe.event.RecipeDetailEvent

sealed class RecipeListEvent {

    data object OnCloseDialog: RecipeListEvent()

    data class OnTriggerDataEvent(val event: RecipeListDataEvent): RecipeListEvent()

    data class OnItemClick(val id: Int): RecipeListEvent()

    data class OnQueryChange(val query: String): RecipeListEvent()

    data class OnThemeChange(val theme: ThemeState): RecipeListEvent()

    data class OnSelectedCategoryChange(val category: String): RecipeListEvent()

    data class OnScrollPositionChange(val position: Int): RecipeListEvent()

}