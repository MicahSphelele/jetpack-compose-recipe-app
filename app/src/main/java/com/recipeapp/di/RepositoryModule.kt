package com.recipeapp.di

import com.recipeapp.network.RecipeService
import com.recipeapp.network.model.RecipeDtoMapper
import com.recipeapp.repository.RecipeRepository
import com.recipeapp.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providesRecipeRepository(recipeService: RecipeService,mapper: RecipeDtoMapper) : RecipeRepository {
        return RecipeRepository_Impl(recipeService,mapper)
    }
}