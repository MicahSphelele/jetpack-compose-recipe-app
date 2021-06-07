package com.recipeapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.recipeapp.util.AppConstants
import com.recipeapp.util.AppLogger
import com.recipeapp.util.DataStoreManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@HiltAndroidApp
class BaseApp : Application(), DefaultLifecycleObserver {

    val isDarkTheme = mutableStateOf(false)
    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate() {
        super<Application>.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
         lifecycleOwner = ProcessLifecycleOwner.get()

        dataStoreManager = DataStoreManager(this.applicationContext)

        lifecycleOwner.lifecycleScope.launch {
            isDarkTheme.value = dataStoreManager.getBoolean(AppConstants.IS_DARK_MODE)
        }
    }


    fun toggleAppTheme() {
        isDarkTheme.value = !isDarkTheme.value
        lifecycleOwner.lifecycleScope.launch {
            AppLogger.info("Saving theme mode is dark mode to : ${isDarkTheme.value}")
            dataStoreManager.saveBoolean(AppConstants.IS_DARK_MODE, isDarkTheme.value)
        }
    }

    fun toggleAppTheme(uiModeState :Boolean) {

        isDarkTheme.value = uiModeState

        lifecycleOwner.lifecycleScope.launch {
            AppLogger.info("Saving theme mode is dark mode to : ${isDarkTheme.value}")
            dataStoreManager.saveBoolean(AppConstants.IS_DARK_MODE, isDarkTheme.value)
        }
    }

}