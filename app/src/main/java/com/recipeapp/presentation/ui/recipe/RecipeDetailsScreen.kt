package com.recipeapp.presentation.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.recipeapp.presentation.components.*

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailViewModel,
    navController: NavController,
    recipeId: Int
) {

    val scaffoldState = rememberScaffoldState()
    val isLoading = viewModel.isLoading.value
    val recipe = viewModel.recipe.value
    val errorState = viewModel.errorState.value
    val isDialogShowing = remember { mutableStateOf(false) }

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
            scaffoldState = scaffoldState,
            topBar = {
                RecipeDetailsTopBar(title = "Recipe Detail", onBackArrowClick = {
                    navController.navigateUp()
                    viewModel.isLoading.value = false
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

