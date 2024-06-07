package com.recipeapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.domain.model.events.RecipeEvent
import com.recipeapp.domain.model.screengraph.Screen
import com.recipeapp.presentation.ui.recipe.RecipeDetailViewModel
import com.recipeapp.presentation.ui.recipe.RecipeDetailsScreen
import com.recipeapp.presentation.ui.recipe.event.RecipeDetailEvent
import com.recipeapp.presentation.ui.recipe_list.RecipeListScreen

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
            val viewModel = hiltViewModel<RecipeDetailViewModel>()

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            it.arguments?.getInt("recipeId")?.let { recipeId ->

                RecipeDetailsScreen(uiState = uiState) { event ->

                    when (event) {

                        is RecipeDetailEvent.OnStart -> {
                            viewModel.onTriggeredEvent(RecipeEvent.GetDetailedRecipeEvent(id = recipeId))
                        }

                        is RecipeDetailEvent.OnBackPressed -> {
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}