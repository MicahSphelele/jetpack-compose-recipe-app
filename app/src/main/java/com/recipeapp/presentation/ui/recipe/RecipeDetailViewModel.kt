package com.recipeapp.presentation.ui.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeapp.domain.model.ErrorState
import com.recipeapp.domain.model.Recipe
import com.recipeapp.data.network.NetworkServiceBuilder
import com.recipeapp.domain.model.events.RecipeEvent
import com.recipeapp.domain.model.events.RecipeEvent.GetDetailedRecipeEvent
import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.presentation.ui.recipe.state.RecipeDetailUIState
import com.recipeapp.util.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named(NetworkServiceBuilder.NAMED_TOKEN)
    private val token: String,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        const val STATE_KEY_RECIPE_ID = "recipe.state.recipe.id"
    }

    private val _uiState = MutableStateFlow(RecipeDetailUIState())
    val uiState = _uiState.asStateFlow()

    init {
        //Restore if the process dies
        savedStateHandle.get<Int>(STATE_KEY_RECIPE_ID)?.let { id ->
            onTriggeredEvent(GetDetailedRecipeEvent(id = id))
        }
    }

    fun onTriggeredEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when(event) {
                    is GetDetailedRecipeEvent -> {
                        getRecipe(id = event.id)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                AppLogger.error("onTriggeredEvent event error", ex)
            }
        }
    }

    fun updateShowDialogState(isDialogShowing: Boolean) {
        _uiState.value = uiState.value.copy(isDialogShowing = isDialogShowing)
    }

    private suspend fun getRecipe(id: Int) {

        updateLoadingState()

        delay(1000)

        try {
            val recipe = repository.get(token = token, id)
            updateRecipe(recipe = recipe)
            savedStateHandle[STATE_KEY_RECIPE_ID] = recipe.id
        } catch (ex: Exception) {
            ex.printStackTrace()
            AppLogger.error("Something went wrong on the server : ${ex.message}")
            updateShowDialogState(isDialogShowing = true)
            updateErrorState(ErrorState(hasError = true, ex.message))
        }
    }

    private fun updateLoadingState() {
        _uiState.value = uiState.value.copy(isLoading = true)
    }

    private fun updateRecipe(recipe: Recipe) {
        _uiState.value = uiState.value.copy(recipe = recipe, isLoading = false)
    }

    private fun updateErrorState(errorState: ErrorState) {
        _uiState.value = uiState.value.copy(errorState = errorState, isLoading = false)
    }
}