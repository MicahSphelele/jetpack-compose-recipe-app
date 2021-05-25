package com.recipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recipeapp.presentation.BaseApp
import com.recipeapp.presentation.components.*
import com.recipeapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel by viewModels<RecipeListViewModel>()

    @Inject
    lateinit var application: BaseApp

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
            },
            bottomBar = {
                AppBottomBar()
            },
            drawerContent = {

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
    fun AppBottomBar() {
        BottomNavigation(
            elevation = 12.dp,
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            BottomNavigationItem(
                icon = { Icon(Icons.Default.BrokenImage) },
                selected = false,
                onClick = {

                })

            BottomNavigationItem(
                icon = { Icon(Icons.Default.Search) },
                selected = false,
                onClick = {

                })
            BottomNavigationItem(
                icon = { Icon(Icons.Default.AccountBalanceWallet) },
                selected = false,
                onClick = {

                })

        }
    }
}