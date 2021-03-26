package com.recipeapp.presentation.components

import android.util.Log
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.recipeapp.presentation.ui.recipe_list.FoodCategory
import com.recipeapp.presentation.ui.recipe_list.getAllFoodCategories

@Composable
fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    executeSearch: () -> Unit, categoryScrollPosition: Float,
    onSelectedCategoryChange: (String) -> Unit,
    onChangeCategoryScrollPosition: (Float) -> Unit,
    selectedCategory: FoodCategory?
) {
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
                        onQueryChange(it)
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
                            executeSearch()
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

                    scrollState.scrollTo(categoryScrollPosition)

                    FoodCategoryChip(
                        category = category.value,
                        onSelectedCategoryChange = {
                            onSelectedCategoryChange(it)
                            onChangeCategoryScrollPosition(scrollState.value)
                        }, isSelected = selectedCategory == category,
                        onExecuteSearch = {
                            executeSearch()
                        })
                }
            }

        }

    }
}