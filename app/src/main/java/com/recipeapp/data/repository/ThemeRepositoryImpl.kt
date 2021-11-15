package com.recipeapp.data.repository

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.domain.repository.ThemeRepository
import com.recipeapp.util.AppConstants
import com.recipeapp.util.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ThemeRepositoryImpl(private val context: Context, private val scope: CoroutineScope) :
    ThemeRepository {

    private var dataStoreManager: DataStoreManager = DataStoreManager(context)

    private val uiModeState = mutableStateOf(UiState.LIGHT.ordinal)

    init {
        scope.launch {
            dataStoreManager.getInteger(AppConstants.UI_MODE).also { uiModeState.value = it }
        }
    }

    override fun isUIStateInDarkMode(isSystemInDarkTheme: Boolean): Boolean {
        return when (uiModeState.value) {
            UiState.LIGHT.ordinal -> {
                false
            }
            UiState.DARK.ordinal -> {
                true
            }
            else -> {
                isSystemInDarkTheme
            }
        }
    }

    override fun changeUiMode(uiModeState: UiState) {
        this.uiModeState.value = uiModeState.ordinal
        scope.launch {
            dataStoreManager.saveInteger(AppConstants.UI_MODE, uiModeState.ordinal)
        }
    }
}