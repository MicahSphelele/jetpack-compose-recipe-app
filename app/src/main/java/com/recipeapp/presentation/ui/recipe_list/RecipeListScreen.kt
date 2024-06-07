package com.recipeapp.presentation.ui.recipe_list

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import com.recipeapp.presentation.components.AppAlertDialog
import com.recipeapp.presentation.components.RecipeList
import com.recipeapp.presentation.components.SearchAppBar
import com.recipeapp.presentation.ui.recipe_list.event.RecipeListEvent
import com.recipeapp.presentation.ui.recipe_list.state.RecipeListUIState

@ExperimentalComposeUiApi
@Composable
fun RecipeListScreen(
    uiState: RecipeListUIState = RecipeListUIState(),
    onEvent: (RecipeListEvent) -> Unit
) {

    var isDialogShowing by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    if (!uiState.errorState.hasError) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = uiState.query,
                    selectedCategory = uiState.selectedCategory,
                    onQueryChange = { query ->
                         onEvent(RecipeListEvent.OnQueryChange(query = query))
                    },
                    onSelectedCategoryChange = { category ->
                        onEvent(RecipeListEvent.OnSelectedCategoryChange(category = category))
                    },
                    onExecuteSearch = { event ->
                        onEvent(RecipeListEvent.OnTriggerDataEvent(event = event))
                    },
                    onChangeUiMode = { theme ->
                        onEvent(RecipeListEvent.OnThemeChange(theme = theme))
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }

        ) { contentPadding ->
            RecipeList(
                contentPadding = contentPadding,
                loading = uiState.isLoading,
                recipes = uiState.recipes,
                page = uiState.page,
                snackbarHostState = snackbarHostState,
                onChangeRecipeListScrollPosition = { position ->
                    onEvent(RecipeListEvent.OnScrollPositionChange(position = position))
                },
                onTriggeredEvent = { event ->
                    onEvent(RecipeListEvent.OnTriggerDataEvent(event = event))
                },
                onItemClick = { id ->
                    onEvent(RecipeListEvent.OnItemClick(id = id))
                },
            )
        }

    } else {

        isDialogShowing = true

        AppAlertDialog(
            title = "Network Error",
            message = "Something went wrong : ${uiState.errorState.errorMessage}",
            buttonText = "Ok",
            isShowing = isDialogShowing,
            onClose = {
                isDialogShowing = false
            }
        )
    }
}