package com.recipeapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@HiltAndroidApp
class BaseApp : Application(), DefaultLifecycleObserver{

    private val lifecycleOwner = ProcessLifecycleOwner.get()

    val isDarkTheme = mutableStateOf(false)

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onCreate() {
        lifecycleOwner.lifecycleScope.launch {
            
        }
    }



    fun toggleAppTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }

}