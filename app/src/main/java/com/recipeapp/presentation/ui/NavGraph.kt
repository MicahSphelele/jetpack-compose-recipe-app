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
import com.recipeapp.presentation.ui.recipe_list.RecipeListViewModel
import com.recipeapp.presentation.ui.recipe_list.event.RecipeListEvent
import com.recipeapp.util.AppLogger

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

            val viewModel = hiltViewModel<RecipeListViewModel>()

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            RecipeListScreen(uiState = uiState) { event ->

                when(event) {

                    is RecipeListEvent.OnTriggerDataEvent -> {
                        viewModel.onTriggeredEvent(event = event.event)
                    }

                    is RecipeListEvent.OnItemClick -> {
                        navController.navigate(Screen.RecipeDetailsScreen.buildRoute(id = event.id))
                    }

                    is RecipeListEvent.OnQueryChange -> {
                        viewModel.onQueryChange(query = event.query)
                    }

                    is RecipeListEvent.OnThemeChange -> {
                        onChangeTheme(event.theme)
                    }

                    is RecipeListEvent.OnSelectedCategoryChange -> {
                        viewModel.onSelectedCategoryChange(category = event.category)
                    }

                    is RecipeListEvent.OnScrollPositionChange -> {
                        viewModel.onChangeRecipeListScrollPosition(position = event.position)
                    }
                }
            }
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

                        is RecipeDetailEvent.OnCloseDialog -> {
                            viewModel.updateShowDialogState(isDialogShowing = false)
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}