package com.recipeapp.di

import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.domain.repository.ThemeRepository
import com.recipeapp.domain.usecases.GetRecipeDetailsUseCase
import com.recipeapp.domain.usecases.GetRecipeListUseCase
import com.recipeapp.domain.usecases.theme.ChangeThemeStateUseCase
import com.recipeapp.domain.usecases.theme.GetThemeStateUseCase
import com.recipeapp.domain.usecases.theme.ThemeUseCases
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

    @Singleton
    @Provides
    fun providesThemeUseCases(
        repository: ThemeRepository
    ): ThemeUseCases =
        ThemeUseCases(
            GetThemeStateUseCase(repository),
            ChangeThemeStateUseCase(repository)
        )
}