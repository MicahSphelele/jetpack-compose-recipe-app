package com.recipeapp.di

import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.domain.usecases.GetRecipeDetailsUseCase
import com.recipeapp.domain.usecases.GetRecipeListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun providesGetRecipeListUseCase(
        repository: RecipeRepository
    ): GetRecipeListUseCase = GetRecipeListUseCase(repository = repository)

    @Singleton
    @Provides
    fun providesGetRecipeDetailsUseCase(
        repository: RecipeRepository
    ): GetRecipeDetailsUseCase = GetRecipeDetailsUseCase(repository = repository)
}