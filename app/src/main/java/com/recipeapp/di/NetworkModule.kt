package com.recipeapp.di

import com.recipeapp.network.NetworkServiceBuilder
import com.recipeapp.network.RecipeService
import com.recipeapp.network.model.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesRecipeService() : RecipeService {
        return NetworkServiceBuilder.buildService(RecipeService::class.java)
    }

    @Singleton
    @Provides
    fun providesRecipeMapper() : RecipeDtoMapper{
        return RecipeDtoMapper()
    }

    @Singleton
    @Provides
    @Named(NetworkServiceBuilder.NAMED_TOKEN)
    fun providesToken() : String{
        return NetworkServiceBuilder.TOKEN
    }
}