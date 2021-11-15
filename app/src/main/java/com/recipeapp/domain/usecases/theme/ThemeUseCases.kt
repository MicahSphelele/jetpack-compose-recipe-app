package com.recipeapp.domain.usecases.theme

import javax.inject.Inject

data class ThemeUseCases @Inject constructor(
    val getThemeStateUseCase: GetThemeStateUseCase,
    val changeThemeStateUseCase: ChangeThemeStateUseCase
)