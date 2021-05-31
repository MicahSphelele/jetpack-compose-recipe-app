package com.recipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.components.*
import com.recipeapp.presentation.theme.AppTheme
import com.recipeapp.presentation.ui.recipe_list.RecipeListViewModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel by viewModels<RecipeListViewModel>()

    @Inject
    lateinit var application: BaseApp

    private val snackbarController = SnackbarController(lifecycleScope)

    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {


                AppTheme(darkTheme = application.isDarkTheme.value) {

                    ViewRecipeList()

                    val errorState = viewModel.errorState.value

                    if (errorState.hasError) {
                        AppAlertDialog(
                            activity = requireActivity(),
                            title = "Network Error",
                            message = "Something went wrong : ${errorState.errorMessage}",
                            buttonText = "Ok",
                            state = mutableStateOf(true)
                        )
                    }
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    private fun ViewRecipeList() {
        val recipes = viewModel.recipes.value
        val query = viewModel.query.value
        val selectedCategory = viewModel.selectedFoodCategory.value
        val scaffoldState = rememberScaffoldState()
        val page = viewModel.page.value

        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    onExecuteSearch = {
                        if (viewModel.selectedFoodCategory.value?.value == "Milk") {
                            lifecycleScope.launch {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    message = "Milk category selected",
                                    actionLabel = "Dismiss"
                                )
                            }
                        }
                    },
                    categoryScrollPosition = viewModel.categoryScrollPosition,
                    onSelectedCategoryChange = viewModel::onSelectedCategoryChange,
                    onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onToggleTheme = {
                        application.toggleAppTheme()
                    }
                )
            }, scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }

        ) {
            val loading = viewModel.loading.value

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.surface)
            ) {
                if (loading && recipes.isEmpty()) {

                    LoadingRecipeListShimmer(cardHeight = 250.dp)

                } else {
                    LazyColumn {
                        itemsIndexed(items = recipes) { index, recipe ->
                            viewModel.onChangeRecipeListScrollPosition(index)

                            if ((index + 1) >= (page * RecipeListViewModelConstants.PAGE_SIZE) && !loading) {
                                viewModel.nextPage()
                            }
                            RecipeCard(recipe = recipe, onClick = {

                            })
                        }
                    }
                }

                CircularIndeterminateProgressBar(isDisplayed = loading)
                DefaultSnackbar(snackbarHostSate = scaffoldState.snackbarHostState, onDismiss = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                }, modifier = Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}
