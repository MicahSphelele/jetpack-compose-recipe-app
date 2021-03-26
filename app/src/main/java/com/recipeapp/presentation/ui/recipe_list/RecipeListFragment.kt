package com.recipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recipeapp.presentation.components.FoodCategoryChip
import com.recipeapp.presentation.components.RecipeCard
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
                    AppAlertDialog(activity = requireActivity(),title = "Network Error",
                        message = "Something went wrong : ${errorState.errorMessage}"
                        , buttonText = "Ok", state = mutableStateOf(true))

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

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.White,
                elevation = 8.dp
            ) {

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        TextField(
                            value = query,
                            onValueChange = {
                                viewModel.onQueryChange(it)
                            },
                            modifier = Modifier.fillMaxWidth(0.9f)
                                .padding(8.dp),
                            label = {
                                Text(text = "Search")
                            }, keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Search
                            ),
                            leadingIcon = { Icon(Icons.Filled.Search) },
                            onImeActionPerformed = { action, softKeyboardController ->
                                if (action == ImeAction.Search) {

                                    viewModel.search()
                                    softKeyboardController?.hideSoftwareKeyboard()
                                }
                            },
                            textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                            backgroundColor = MaterialTheme.colors.surface
                        )
                    }

                    val scrollState = rememberScrollState()

                    ScrollableRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 8.dp,
                                bottom = 8.dp
                            ),
                        scrollState = scrollState
                    ) {
                        getAllFoodCategories().forEach { category ->

                            scrollState.scrollTo(viewModel.categoryScrollPosition)

                            FoodCategoryChip(
                                category = category.value,
                                onSelectedCategoryChange = {
                                    viewModel.onSelectedCategoryChange(it)
                                    viewModel.onChangeCategoryScrollPosition(scrollState.value)
                                }, isSelected = selectedCategory == category,
                                onExecuteSearch = {
                                    viewModel.search()
                                })
                        }
                    }

                }

            }

            LazyColumn {
                itemsIndexed(items = recipes) { _, recipe ->
                    RecipeCard(recipe = recipe, onClick = {

                    })
                }
            }
        }
    }
}