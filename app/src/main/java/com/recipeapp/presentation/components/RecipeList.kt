package com.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.model.screengraph.Screen
import com.recipeapp.domain.model.events.RecipeListEvent
import com.recipeapp.presentation.ui.recipe_list.RecipeListViewModel
import com.recipeapp.util.AppLogger

@Composable
fun RecipeList(
    contentPaddingValues: PaddingValues,
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeListScrollPosition: (Int) -> Unit,
    onTriggeredEvent: (RecipeListEvent) -> Unit,
    navController: NavController,
    page: Int,
    snackbarHostState: SnackbarHostState
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        if (loading && recipes.isEmpty()) {

            LoadingRecipeListShimmer(cardHeight = 250.dp)

        } else {
            LazyColumn {
                itemsIndexed(items = recipes) { index, recipe ->
                    onChangeRecipeListScrollPosition(index)
                    if ((index + 1) >= (page * RecipeListViewModel.PAGE_SIZE) && !loading) {
                        onTriggeredEvent(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(recipe = recipe, onClick = {
                        recipe.id?.let { recipeID ->
                            AppLogger.info("recipeId: $recipeID")
                            navController.navigate("${Screen.RecipeDetailsScreen.route}/${recipeID}")
                        }
                    })
                }
            }
        }

        CircularIndeterminateProgressBar(isDisplayed = loading)
        DefaultSnackbar(
            snackbarHostSate = snackbarHostState,
            onDismiss = {
                snackbarHostState.currentSnackbarData?.dismiss()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}