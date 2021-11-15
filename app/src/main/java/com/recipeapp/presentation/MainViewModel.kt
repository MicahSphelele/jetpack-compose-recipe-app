package com.recipeapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.domain.usecases.theme.ThemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val themeUseCases: ThemeUseCases) : ViewModel() {

    val isDarkMode =  mutableStateOf(false)

    fun isUIStateInDarkMode(isSystemInDarkTheme: Boolean) =
        themeUseCases.getThemeStateUseCase(viewModelScope,isSystemInDarkTheme) {
            isDarkMode.value = it
        }

    fun changeUiMode(uiState: UiState) = themeUseCases.changeThemeStateUseCase(uiState)
}