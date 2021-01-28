package com.recipeapp.di

import android.content.Context
import com.recipeapp.network.NetworkServiceBuilder
import com.recipeapp.network.RecipeService
import com.recipeapp.presentation.BaseApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext app: Context) : BaseApp {
        return app as BaseApp
    }

}