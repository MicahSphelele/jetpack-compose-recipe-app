package com.recipeapp.di

import com.recipeapp.data.network.NetworkServiceBuilder
import com.recipeapp.data.network.api.RecipeService
import com.recipeapp.domain.util.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesRecipeService(): RecipeService {
        return NetworkServiceBuilder.buildService(RecipeService::class.java)
    }

    @Singleton
    @Provides
    fun providesRecipeMapper(): RecipeDtoMapper {
        return RecipeDtoMapper()
    }

    @Singleton
    @Provides
    @Named(NetworkServiceBuilder.NAMED_TOKEN)
    fun providesToken(): String {
        return NetworkServiceBuilder.TOKEN
    }
}