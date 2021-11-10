package com.recipeapp.data.repository

import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.data.network.api.RecipeService
import com.recipeapp.domain.util.RecipeDtoMapper

class RecipeRepositoryImpl(private val recipeService: RecipeService, private val mapper: RecipeDtoMapper) :
    RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(recipeService.search(token,page,query).recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.get(token,id))
    }
}