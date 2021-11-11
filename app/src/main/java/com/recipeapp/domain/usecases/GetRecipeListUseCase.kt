package com.recipeapp.domain.usecases

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetRecipeListUseCase @Inject constructor(private val repository: RecipeRepository) {

    operator fun invoke(coroutineScope: CoroutineScope,token: String, page: Int, query: String): MutableState<List<Recipe>> {

        val mutableState = mutableStateOf<List<Recipe>>(listOf())

        coroutineScope.launch {
            mutableState.value = repository.search(token = token, page = page, query = query)
        }

        return mutableState
    }
}