package com.recipeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recipeapp.domain.model.enums.ThemeState
import com.recipeapp.domain.usecases.theme.ThemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val themeUseCases: ThemeUseCases) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    fun isUIStateInDarkMode(isSystemInDarkTheme: Boolean) =
        themeUseCases.getThemeStateUseCase(
            viewModelScope, isSystemInDarkTheme
        ) {
            _isDarkMode.value = it
        }

    fun changeUiMode(themeState: ThemeState) = themeUseCases.changeThemeStateUseCase(themeState)
}