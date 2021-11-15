package com.recipeapp.data.repository

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.domain.repository.ThemeRepository
import com.recipeapp.util.AppConstants
import com.recipeapp.util.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ThemeRepositoryImpl(context: Context, private val scope: CoroutineScope) :
    ThemeRepository {

    private var dataStoreManager: DataStoreManager = DataStoreManager(context)

    private val uiState = mutableStateOf(UiState.LIGHT.ordinal)

    override fun isUIStateInDarkMode(
        scope: CoroutineScope,
        isSystemInDarkTheme: Boolean,
        onChangeTheme: (Boolean) -> Unit
    ) {

        scope.launch {
            uiState.value = dataStoreManager.getInteger(AppConstants.UI_MODE)
        }

         when (uiState.value) {
            UiState.LIGHT.ordinal -> {
                onChangeTheme(false)
            }
            UiState.DARK.ordinal -> {
                onChangeTheme(true)

            }
            else -> {
                onChangeTheme(isSystemInDarkTheme)
            }
        }
    }

    override fun changeUiMode(uiModeState: UiState) {
        this.uiState.value = uiModeState.ordinal
        scope.launch {
            dataStoreManager.saveInteger(AppConstants.UI_MODE, uiModeState.ordinal)
        }
    }
}