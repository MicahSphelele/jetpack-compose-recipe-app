package com.recipeapp.di

import android.content.Context
import com.recipeapp.data.network.api.RecipeService
import com.recipeapp.data.repository.RecipeRepositoryImpl
import com.recipeapp.data.repository.ThemeRepositoryImpl
import com.recipeapp.domain.repository.RecipeRepository
import com.recipeapp.domain.repository.ThemeRepository
import com.recipeapp.domain.util.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providesRecipeRepository(
        recipeService: RecipeService,
        mapper: RecipeDtoMapper
    ): RecipeRepository = RecipeRepositoryImpl(recipeService, mapper)

    @Singleton
    @Provides
    fun providesThemeRepository(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): ThemeRepository = ThemeRepositoryImpl(context, scope)
}