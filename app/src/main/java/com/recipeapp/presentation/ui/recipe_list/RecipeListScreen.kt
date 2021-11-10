package com.recipeapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.components.AppAlertDialog
import com.recipeapp.presentation.components.RecipeList
import com.recipeapp.presentation.components.SearchAppBar
import com.recipeapp.presentation.ui.recipe_list.RecipeListViewModel

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel,
    application: BaseApp,
    navController: NavController
) {
    val recipes = viewModel.recipes.value
    val query = viewModel.query.value
    val selectedCategory = viewModel.selectedFoodCategory.value
    val scaffoldState = rememberScaffoldState()
    val page = viewModel.page.value
    val errorState = viewModel.errorState.value
    val isLoading = viewModel.loading.value
    val isDialogShowing = remember { mutableStateOf(false) }

    if (!errorState.hasError) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    onExecuteSearch = viewModel::onTriggeredEvent,
                    onSelectedCategoryChange = viewModel::onSelectedCategoryChange,
                    selectedCategory = selectedCategory,
                    onChangeUiMode = application::onChangeUiMode
                )
            },
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }

        ) {
            RecipeList(
                loading = isLoading,
                recipes = recipes,
                onChangeRecipeListScrollPosition = viewModel::onChangeRecipeListScrollPosition,
                onTriggeredEvent = viewModel::onTriggeredEvent,
                navController = navController,
                page = page,
                scaffoldState = scaffoldState
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