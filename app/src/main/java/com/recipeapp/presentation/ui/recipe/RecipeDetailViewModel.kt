package com.recipeapp.presentation.ui.recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeapp.domain.model.ErrorState
import com.recipeapp.domain.model.Recipe
import com.recipeapp.data.network.NetworkServiceBuilder
import com.recipeapp.presentation.ui.recipe.RecipeEvent.GetDetailedRecipeEvent
import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.util.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val errorState: MutableState<ErrorState> = mutableStateOf(ErrorState(false, null))

    init {
        //Restore if the process dies
        savedStateHandle.get<Int>(STATE_KEY_RECIPE_ID)?.let { recipeID ->
            onTriggeredEvent(GetDetailedRecipeEvent(id = recipeID))
        }
    }

    fun onTriggeredEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when(event) {
                    is GetDetailedRecipeEvent -> {
                        getRecipe(event.id)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                AppLogger.error("onTriggeredEvent event error", ex)
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true
        delay(1000)
        try {
            val recipe = repository.get(token = token, id)
            this.recipe.value = recipe
            savedStateHandle.set(STATE_KEY_RECIPE_ID, recipe.id)
            loading.value = false
        } catch (ex: Exception) {
            ex.printStackTrace()
            AppLogger.error("Something went wrong on the server : ${ex.message}")
            errorState.value = ErrorState(true, ex.message)
        }
    }

}