package com.recipeapp.domain.usecases.theme

import com.recipeapp.domain.repository.ThemeRepository

class GetThemeStateUseCase(private val repository: ThemeRepository) {

    operator fun invoke(isSystemInDarkTheme: Boolean): Boolean =
        repository.isUIStateInDarkMode(isSystemInDarkTheme)
}