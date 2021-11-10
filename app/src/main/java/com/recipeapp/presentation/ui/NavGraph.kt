package com.recipeapp.presentation.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.Screen
import com.recipeapp.presentation.ui.recipe.RecipeDetailViewModel
import com.recipeapp.presentation.ui.recipe.RecipeEvent
import com.recipeapp.presentation.ui.recipe_list.RecipeListViewModel

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun SetupNavGraph(
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
            RecipeListScreen(recipeListViewModel, application, navController)
        }

        composable(
            route = "${Screen.RecipeDetailsScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            if (it.lifecycle.currentState == Lifecycle.State.STARTED ) {
                it.arguments?.getInt("id")?.let { id ->
                    recipeDetailsViewModel.onTriggeredEvent(RecipeEvent.GetDetailedRecipeEvent(id))
                }
            }
            RecipeDetailsScreen(recipeDetailsViewModel, navController)
        }
    }
}