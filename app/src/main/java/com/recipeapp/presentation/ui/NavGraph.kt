package com.recipeapp.presentation.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.domain.model.screengraph.Screen
import com.recipeapp.presentation.ui.recipe.RecipeDetailsScreen
import com.recipeapp.presentation.ui.recipe_list.RecipeListScreen

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RecipeAppNavGraph(
    navController: NavHostController,
    onChangeTheme: (UiState) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Screen.RecipeListScreen.route
    ) {
        composable(route = Screen.RecipeListScreen.route) {
            RecipeListScreen(
                navController = navController,
                onChangeTheme = onChangeTheme
            )
        }

        composable(
            route = "${Screen.RecipeDetailsScreen.route}/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) {
            it.arguments?.getInt("recipeId")?.let { recipeId ->
                RecipeDetailsScreen(navController, recipeId)
            }
        }
    }
}