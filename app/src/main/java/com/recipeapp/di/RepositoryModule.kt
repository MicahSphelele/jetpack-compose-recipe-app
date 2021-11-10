package com.recipeapp.di

import com.recipeapp.data.network.api.RecipeService
import com.recipeapp.data.repository.RecipeRepositoryImpl
import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.domain.util.RecipeDtoMapper
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