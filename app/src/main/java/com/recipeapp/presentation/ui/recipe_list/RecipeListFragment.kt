package com.recipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.components.*
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

    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                val snackbarHostState = remember { SnackbarHostState() }

                Column {
                    Button(onClick = {
                        lifecycleScope.launch {
                            snackbarHostState.showSnackbar(message = "Hey look a snackbar", actionLabel = "Hide", duration = SnackbarDuration.Short)
                        }

                    }) {
                        Text(text = "Click Me")
                    }
                    
                    DecoupledSnackbar(snackbarHostState = snackbarHostState)
                }
                

//                AppTheme(darkTheme = application.isDarkTheme.value) {
//
//                    ViewRecipeList()
//
//                    val errorState = viewModel.errorState.value
//
//                    if (errorState.hasError) {
//                        AppAlertDialog(
//                            activity = requireActivity(),
//                            title = "Network Error",
//                            message = "Something went wrong : ${errorState.errorMessage}",
//                            buttonText = "Ok",
//                            state = mutableStateOf(true)
//                        )
//                    }
//                }
            }
        }
    }

    @Composable
    private fun ViewRecipeList() {
        val recipes = viewModel.recipes.value
        val query = viewModel.query.value
        val selectedCategory = viewModel.selectedFoodCategory.value

        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChange = viewModel::onQueryChange,
                    executeSearch = viewModel::search,
                    categoryScrollPosition = viewModel.categoryScrollPosition,
                    onSelectedCategoryChange = viewModel::onSelectedCategoryChange,
                    onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onToggleTheme = {
                        application.toggleAppTheme()
                    }
                )
            }

        ) {
            val loading = viewModel.loading.value

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.surface)
            ) {
                if (loading) {

                    LoadingRecipeListShimmer(cardHeight = 250.dp)

                } else {
                    LazyColumn {
                        itemsIndexed(items = recipes) { _, recipe ->
                            RecipeCard(recipe = recipe, onClick = {

                            })
                        }
                    }
                }

                CircularIndeterminateProgressBar(isDisplayed = loading)
            }
        }
    }

    @Composable
    private fun SnackBarDemo(isShowing: Boolean,onHideSnackbar: () -> Unit) {
        if (isShowing) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val snackbar = createRef()

                Snackbar(modifier = Modifier.constrainAs(snackbar) {
                    this.bottom.linkTo(parent.bottom)
                    this.start.linkTo(parent.start)
                    this.end.linkTo(parent.end)
                }, action = {
                    Text(text = "Hide",
                        modifier = Modifier.clickable(onClick = onHideSnackbar),
                        style = MaterialTheme.typography.h5)
                }) {
                    Text(text = "Hey look a snackbar")
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    private fun DecoupledSnackbar(snackbarHostState: SnackbarHostState) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()

            SnackbarHost(modifier = Modifier.constrainAs(snackbar) {
                this.bottom.linkTo(parent.bottom)
                this.start.linkTo(parent.start)
                this.end.linkTo(parent.end)
            } ,hostState = snackbarHostState,
                snackbar = {
             
                    Snackbar(action = {
                        TextButton(onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }) {
                            Text(text = "Hide", style = TextStyle(color = Color.White))
                        }
                    }) {
                        Text(text = "Hey look a snackbar")
                    }
                })
        }
    }
}