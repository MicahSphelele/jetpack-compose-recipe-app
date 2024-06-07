package com.recipeapp.domain.repository

import com.recipeapp.domain.model.enums.ThemeState
import kotlinx.coroutines.CoroutineScope

interface ThemeRepository {
    fun isUIStateInDarkMode(
        scope: CoroutineScope,
        isSystemInDarkTheme: Boolean,
        onChangeTheme: (Boolean) -> Unit
    )
    fun changeUiMode(uiModeState: ThemeState)
}