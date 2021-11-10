package com.recipeapp.di

import com.recipeapp.network.RecipeService
import com.recipeapp.domain.util.RecipeDtoMapper
import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.network.repository.RecipeRepositoryImpl
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
    fun providesRecipeRepository(
        recipeService: RecipeService,
        mapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, mapper)
    }
}