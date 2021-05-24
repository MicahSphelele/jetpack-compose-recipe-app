package com.recipeapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : Application() {

    val isDarkTheme = mutableStateOf(false)

    fun toggleAppTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}