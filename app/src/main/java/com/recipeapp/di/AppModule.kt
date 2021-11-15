package com.recipeapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.recipeapp.presentation.BaseApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext context: Context): BaseApp =
        (context as BaseApp)

    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default + Job())

}