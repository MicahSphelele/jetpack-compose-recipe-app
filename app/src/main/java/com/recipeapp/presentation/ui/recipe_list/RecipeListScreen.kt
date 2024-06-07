package com.recipeapp.presentation.ui.recipe_list

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.presentation.components.AppAlertDialog
import com.recipeapp.presentation.components.RecipeList
import com.recipeapp.presentation.components.SearchAppBar

@ExperimentalComposeUiApi
@Composable
fun RecipeListScreen(
    navController: NavController,
    onChangeTheme: (UiState) -> Unit
) {
    val viewModel = hiltViewModel<RecipeListViewModel>()
    val recipes = viewModel.recipes.value
    val query = viewModel.query.value
    val selectedCategory = viewModel.selectedFoodCategory.value
    val page = viewModel.page.intValue
    val errorState = viewModel.errorState.value
    val isLoading = viewModel.loading.value
    val isDialogShowing = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    if (!errorState.hasError) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    onExecuteSearch = viewModel::onTriggeredEvent,
                    onSelectedCategoryChange = viewModel::onSelectedCategoryChange,
                    selectedCategory = selectedCategory,
                    onChangeUiMode = onChangeTheme
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }

        ) { contentPadding ->
            RecipeList(
                contentPadding = contentPadding,
                loading = isLoading,
                recipes = recipes,
                onChangeRecipeListScrollPosition = viewModel::onChangeRecipeListScrollPosition,
                onTriggeredEvent = viewModel::onTriggeredEvent,
                navController = navController,
                page = page,
                snackbarHostState = snackbarHostState
            )
        }
        return
    }
    isDialogShowing.value = true
    AppAlertDialog(
        activity = (LocalContext.current as AppCompatActivity),
        title = "Network Error",
        message = "Something went wrong : ${errorState.errorMessage}",
        buttonText = "Ok",
        isShowing = isDialogShowing
    )
}