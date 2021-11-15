package com.recipeapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.util.AppConstants
import com.recipeapp.util.AppLogger
import com.recipeapp.util.DataStoreManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@HiltAndroidApp
class BaseApp : Application(), DefaultLifecycleObserver {

//    private val uiModeState = mutableStateOf(UiState.LIGHT.ordinal)
//
//    private lateinit var lifecycleOwner: LifecycleOwner
//    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate() {
        super<Application>.onCreate()
//        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
//        lifecycleOwner = ProcessLifecycleOwner.get()
//
//        dataStoreManager = DataStoreManager(this.applicationContext)
//
//        lifecycleOwner.lifecycleScope.launch {
//            //isDarkTheme.value = dataStoreManager.getBoolean(AppConstants.IS_DARK_MODE)
//            uiModeState.value = dataStoreManager.getInteger(AppConstants.UI_MODE)
//            AppLogger.info("UI State = ${uiModeState.value}")
//        }
    }

//    fun isUIStateInDarkMode(isSystemInDarkTheme: Boolean): Boolean {
//        return when (uiModeState.value) {
//            UiState.LIGHT.ordinal -> {
//                false
//            }
//            UiState.DARK.ordinal -> {
//
//                true
//
//            } else -> {
//                isSystemInDarkTheme
//            }
//        }
//    }

//    fun onChangeUiMode(uiModeState: UiState) {
//
//        this.uiModeState.value = uiModeState.ordinal
//
//        lifecycleOwner.lifecycleScope.launch {
//            dataStoreManager.saveInteger(AppConstants.UI_MODE, uiModeState.ordinal)
//        }
//    }
}