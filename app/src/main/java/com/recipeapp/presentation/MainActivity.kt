package com.recipeapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.recipeapp.presentation.theme.AppTheme
import com.recipeapp.presentation.ui.RecipeAppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel by viewModels<MainViewModel>()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()

            viewModel.isUIStateInDarkMode(isSystemInDarkTheme = isSystemInDarkTheme)

            val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()

            AppTheme(isDarkMode = isDarkMode) {

                RecipeAppNavGraph(
                    navController = rememberNavController(),
                    onChangeTheme = viewModel::changeUiMode
                )
            }
        }
    }
}


