package com.recipeapp.domain.usecases.theme

import com.recipeapp.domain.model.enums.ThemeState
import com.recipeapp.domain.repository.ThemeRepository

class ChangeThemeStateUseCase(private val repository: ThemeRepository) {
    operator fun invoke(themeState: ThemeState) = repository.changeUiMode(themeState)
}