package com.recipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.components.AppAlertDialog
import com.recipeapp.presentation.components.RecipeList
import com.recipeapp.presentation.components.SearchAppBar
import com.recipeapp.presentation.components.SnackbarController
import com.recipeapp.presentation.theme.AppTheme
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
                    onExecuteSearch = viewModel::onTriggeredEvent,
                    categoryScrollPosition = viewModel.categoryScrollPosition,
                    onSelectedCategoryChange = viewModel::onSelectedCategoryChange,
                    onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onToggleTheme = {
                        application.toggleAppTheme()
                        lifecycleScope.launch {
                            val message = if(application.isDarkTheme.value) {
                                "Changed app them to dark"
                            } else {
                                "Changed app them to light"
                            }
                            snackbarController.showSnackbar(
                                scaffoldState = scaffoldState,
                                message = message,
                                actionLabel = "Dismiss"
                            )
                        }
                    }
                )
            }, scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            }

        ) {
            val loading = viewModel.loading.value

            RecipeList(
                loading = loading,
                recipes = recipes,
                onChangeRecipeListScrollPosition = viewModel::onChangeRecipeListScrollPosition,
                onTriggeredEvent = viewModel::onTriggeredEvent,
                findNavController(),
                page = page,
                scaffoldState = scaffoldState
            )
        }
    }
}
