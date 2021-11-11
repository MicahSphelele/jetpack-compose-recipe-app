package com.recipeapp.domain.usecases

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetRecipeDetailsUseCase @Inject constructor(private val repository: RecipeRepository) {

    operator fun invoke(
        coroutineScope: CoroutineScope,
        token: String,
        recipeId: Int
    ): MutableState<Recipe> {

        val mutableState = mutableStateOf<Recipe>(Recipe())

        coroutineScope.launch {
            mutableState.value = repository.get(token = token, id = recipeId)
        }

        return mutableState
    }
}