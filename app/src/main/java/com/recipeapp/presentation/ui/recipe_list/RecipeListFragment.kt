package com.recipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recipeapp.presentation.components.RecipeCard
import com.recipeapp.presentation.components.SearchAppBar
import com.recipeapp.presentation.ui.AppAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel by viewModels<RecipeListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

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

    @Composable
    fun ViewRecipeList() {
        val recipes = viewModel.recipes.value
        val query = viewModel.query.value
        val selectedCategory = viewModel.selectedFoodCategory.value

        Column {
            SearchAppBar(
                query = query,
                onQueryChange = viewModel::onQueryChange ,
                executeSearch = viewModel::search ,
                categoryScrollPosition = viewModel.categoryScrollPosition,
                onSelectedCategoryChange = viewModel::onSelectedCategoryChange,
                onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                selectedCategory = selectedCategory
            )
            LazyColumn {
                itemsIndexed(items = recipes) { _, recipe ->
                    RecipeCard(recipe = recipe, onClick = {

                    })
                }
            }
        }
    }
}