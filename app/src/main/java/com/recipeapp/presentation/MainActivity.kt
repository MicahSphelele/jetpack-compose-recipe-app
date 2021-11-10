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
import com.recipeapp.presentation.ui.recipe.RecipeDetailViewModel
import com.recipeapp.presentation.ui.recipe_list.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModelRecipeList by viewModels<RecipeListViewModel>()
    private val viewModelRecipeDetails by viewModels<RecipeDetailViewModel>()

    @Inject
    lateinit var application: BaseApp

    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme(darkTheme = application.isUIStateInDarkMode(isSystemInDarkTheme = isSystemInDarkTheme())) {

                val navController = rememberNavController()

                RecipeAppNavGraph(
                    navController = navController,
                    application = application,
                    recipeListViewModel = viewModelRecipeList,
                    recipeDetailsViewModel = viewModelRecipeDetails
                )

            }
        }
    }
}


