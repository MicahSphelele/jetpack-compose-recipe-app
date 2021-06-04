package com.recipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.recipeapp.domain.model.Recipe
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.components.CircularIndeterminateProgressBar
import com.recipeapp.presentation.components.LoadingRecipeDetailShimmer
import com.recipeapp.presentation.components.RecipeDetailView
import com.recipeapp.presentation.components.RecipeDetailsTopBar
import com.recipeapp.presentation.theme.AppTheme
import com.recipeapp.util.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    private val viewModel by viewModels<RecipeDetailViewModel>()

    @Inject
    lateinit var application: BaseApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            val recipeID = bundle.getInt(AppConstants.KEY_RECIPE_ID)
            viewModel.onTriggeredEvent(RecipeEvent.GetDetailedRecipeEvent(recipeID))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                AppTheme(darkTheme = application.isDarkTheme.value) {
                    ViewRecipeDetails(recipe = recipe, loading = loading)
                }
            }
        }
    }

    @Composable
    private fun ViewRecipeDetails(
        recipe: Recipe?,
        loading: Boolean
    ) {
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
               RecipeDetailsTopBar(title = "Recipe Detail",onBackArrowClick = {
                   findNavController().navigateUp()
               })
            },
            snackbarHost = { scaffoldState.snackbarHostState }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                if (loading && recipe == null) {

                    LoadingRecipeDetailShimmer(cardHeight = 250.dp)

                } else {
                    recipe?.let {
                        RecipeDetailView(recipe = it)
                    }
                }

                CircularIndeterminateProgressBar(isDisplayed = loading, 0.3f)
            }
        }
    }
}