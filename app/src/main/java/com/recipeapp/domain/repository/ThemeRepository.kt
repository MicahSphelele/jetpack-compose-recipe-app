package com.recipeapp.domain.repository

import com.recipeapp.domain.model.enums.UiState

interface ThemeRepository {
    fun isUIStateInDarkMode(isSystemInDarkTheme: Boolean) : Boolean
    fun changeUiMode(uiModeState: UiState)
}