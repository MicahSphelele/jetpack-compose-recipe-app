package com.recipeapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.recipeapp.presentation.theme.AppTheme
import com.recipeapp.presentation.ui.RecipeAppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel by viewModels<MainViewModel>()

    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()

            viewModel.isUIStateInDarkMode(isSystemInDarkTheme = isSystemInDarkTheme)

            AppTheme(darkTheme = viewModel.isDarkMode.value) {

                val navController = rememberNavController()

                RecipeAppNavGraph(
                    navController = navController,
                    onChangeTheme = viewModel::changeUiMode
                )
            }
        }
    }
}


