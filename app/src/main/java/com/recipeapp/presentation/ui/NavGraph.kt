package com.recipeapp.presentation.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.Screen
import com.recipeapp.presentation.ui.recipe.RecipeDetailViewModel
import com.recipeapp.presentation.ui.recipe.RecipeDetailsScreen
import com.recipeapp.presentation.ui.recipe_list.RecipeListViewModel

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun RecipeAppNavGraph(
    navController: NavHostController,
    application: BaseApp,
    recipeListViewModel: RecipeListViewModel,
    recipeDetailsViewModel: RecipeDetailViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Screen.RecipeListScreen.route
    ) {
        composable(route = Screen.RecipeListScreen.route) {
            RecipeListScreen(
                viewModel = recipeListViewModel,
                application = application,
                navController = navController
            )
        }

        composable(
            route = "${Screen.RecipeDetailsScreen.route}/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) {
            it.arguments?.getInt("recipeId")?.let { recipeId ->
                RecipeDetailsScreen(recipeDetailsViewModel, navController, recipeId)
            }
        }
    }
}