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
import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.model.events.RecipeListDataEvent
import com.recipeapp.presentation.ui.recipe_list.RecipeListViewModel.Companion.PAGE_SIZE

@Composable
fun RecipeList(
    contentPadding: PaddingValues,
    loading: Boolean,
    recipes: List<Recipe>,
    page: Int,
    snackbarHostState: SnackbarHostState,
    onChangeRecipeListScrollPosition: (Int) -> Unit,
    onTriggeredEvent: (RecipeListDataEvent) -> Unit,
    onItemClick: (Int) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        if (loading && recipes.isEmpty()) {

            LoadingRecipeListShimmer(
                contentPadding = contentPadding,
                cardHeight = 250.dp
            )

        } else {
            LazyColumn(contentPadding = contentPadding) {
                itemsIndexed(items = recipes) { index, recipe ->
                    onChangeRecipeListScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onTriggeredEvent(RecipeListDataEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            recipe.id?.let { id ->
                                onItemClick(id)
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