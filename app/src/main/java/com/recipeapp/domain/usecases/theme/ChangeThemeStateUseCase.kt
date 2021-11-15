package com.recipeapp.domain.usecases.theme

import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.domain.repository.ThemeRepository

class ChangeThemeStateUseCase(private val repository: ThemeRepository) {
    operator fun invoke(uiState: UiState) = repository.changeUiMode(uiState)
}