package com.recipeapp.data.repository

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.recipeapp.domain.model.enums.ThemeState
import com.recipeapp.domain.repository.ThemeRepository
import com.recipeapp.util.AppConstants
import com.recipeapp.util.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ThemeRepositoryImpl(context: Context, private val scope: CoroutineScope) :
    ThemeRepository {

    private var dataStoreManager: DataStoreManager = DataStoreManager(context)

    private val themeState = mutableStateOf(ThemeState.LIGHT.ordinal)

    override fun isUIStateInDarkMode(
        scope: CoroutineScope,
        isSystemInDarkTheme: Boolean,
        onChangeTheme: (Boolean) -> Unit
    ) {

        scope.launch {
            themeState.value = dataStoreManager.getInteger(AppConstants.UI_MODE)
        }

         when (themeState.value) {
            ThemeState.LIGHT.ordinal -> {
                onChangeTheme(false)
            }
            ThemeState.DARK.ordinal -> {
                onChangeTheme(true)

            }
            else -> {
                onChangeTheme(isSystemInDarkTheme)
            }
        }
    }

    override fun changeUiMode(uiModeState: ThemeState) {
        this.themeState.value = uiModeState.ordinal
        scope.launch {
            dataStoreManager.saveInteger(AppConstants.UI_MODE, uiModeState.ordinal)
        }
    }
}