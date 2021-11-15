package com.recipeapp.domain.usecases.theme

import com.recipeapp.domain.repository.ThemeRepository
import kotlinx.coroutines.CoroutineScope

class GetThemeStateUseCase(private val repository: ThemeRepository) {

    operator fun invoke(
        scope: CoroutineScope,
        isSystemInDarkTheme: Boolean,
        onChangeTheme: (Boolean) -> Unit
    ) = repository.isUIStateInDarkMode(scope, isSystemInDarkTheme, onChangeTheme)
}