package com.recipeapp.presentation.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.recipeapp.presentation.components.AppAlertDialog
import com.recipeapp.presentation.components.CircularIndeterminateProgressBar
import com.recipeapp.presentation.components.LoadingRecipeDetailShimmer
import com.recipeapp.presentation.components.RecipeDetailView
import com.recipeapp.presentation.components.RecipeDetailsTopBar
import com.recipeapp.presentation.ui.recipe.event.RecipeDetailEvent
import com.recipeapp.presentation.ui.recipe.state.RecipeDetailUIState

@Composable
fun RecipeDetailsScreen(
    uiState: RecipeDetailUIState = RecipeDetailUIState(),
    onEvent:(RecipeDetailEvent) -> Unit,
) {
    val isDialogShowing = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                onEvent(RecipeDetailEvent.OnStart)
                //viewModel.onTriggeredEvent(RecipeEvent.GetDetailedRecipeEvent(recipeId))
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    if (!uiState.errorState.hasError) {
        Scaffold(
            topBar = {
                RecipeDetailsTopBar(title = "Recipe Detail",
                    onBackArrowClick = {
                        onEvent(RecipeDetailEvent.OnBackPressed)
                })
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { contentPadding ->

            if (uiState.isLoading && uiState.recipe == null) {

                LoadingRecipeDetailShimmer(
                    contentPadding = contentPadding,
                    cardHeight = 250.dp
                )

                CircularIndeterminateProgressBar(isDisplayed = true, verticalBias = 0.3f)

            } else {
                uiState.recipe?.let {
                    RecipeDetailView(contentPadding = contentPadding, recipe = it)
                }
            }
        }
        return
    }
    isDialogShowing.value = true
    AppAlertDialog(
        activity = (LocalContext.current as AppCompatActivity),
        title = "Network Error",
        message = "Something went wrong : ${uiState.errorState.errorMessage}",
        buttonText = "Ok",
        isShowing = isDialogShowing
    )
}

