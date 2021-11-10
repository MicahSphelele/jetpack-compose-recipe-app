package com.recipeapp.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.recipeapp.presentation.components.CircularIndeterminateProgressBar
import com.recipeapp.presentation.components.LoadingRecipeDetailShimmer
import com.recipeapp.presentation.components.RecipeDetailView
import com.recipeapp.presentation.components.RecipeDetailsTopBar
import com.recipeapp.presentation.ui.recipe.RecipeDetailViewModel

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailViewModel,
    navController: NavController
) {


    val scaffoldState = rememberScaffoldState()
    val isLoading = viewModel.loading.value
    val recipe = viewModel.recipe.value

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            RecipeDetailsTopBar(title = "Recipe Detail", onBackArrowClick = {
                navController.navigateUp()
                viewModel.loading.value = false
                viewModel.recipe.value = null
            })
        },
        snackbarHost = { scaffoldState.snackbarHostState }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            if (isLoading && recipe == null) {

                LoadingRecipeDetailShimmer(cardHeight = 250.dp)
                CircularIndeterminateProgressBar(isDisplayed = isLoading, 0.3f)

            } else {
                recipe?.let {
                    RecipeDetailView(recipe = it)
                }
            }
        }
    }
}