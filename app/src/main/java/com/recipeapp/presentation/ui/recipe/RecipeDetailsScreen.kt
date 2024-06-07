package com.recipeapp.presentation.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.recipeapp.domain.model.events.RecipeEvent
import com.recipeapp.presentation.components.*

@Composable
fun RecipeDetailsScreen(
    navController: NavController,
    recipeId: Int
) {
    val viewModel = hiltViewModel<RecipeDetailViewModel>()
    val isLoading = viewModel.isLoading.value
    val recipe = viewModel.recipe.value
    val errorState = viewModel.errorState.value
    val isDialogShowing = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.onTriggeredEvent(RecipeEvent.GetDetailedRecipeEvent(recipeId))
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    if (!errorState.hasError) {
        Scaffold(
            topBar = {
                RecipeDetailsTopBar(title = "Recipe Detail", onBackArrowClick = {
                    navController.navigateUp()
                    viewModel.isLoading.value = false
                    viewModel.recipe.value = null
                })
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { contentPadding ->

            if (isLoading && recipe == null) {

                LoadingRecipeDetailShimmer(
                    contentPadding = contentPadding,
                    cardHeight = 250.dp
                )

                CircularIndeterminateProgressBar(isDisplayed = true, verticalBias = 0.3f)

            } else {
                recipe?.let {
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
        message = "Something went wrong : ${errorState.errorMessage}",
        buttonText = "Ok",
        isShowing = isDialogShowing
    )
}

