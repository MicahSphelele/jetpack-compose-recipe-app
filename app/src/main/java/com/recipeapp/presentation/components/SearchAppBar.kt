package com.recipeapp.presentation.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.gson.annotations.Until
import com.recipeapp.presentation.ui.recipe_list.FoodCategory
import com.recipeapp.presentation.ui.recipe_list.getAllFoodCategories

@Composable
fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    executeSearch: () -> Unit, categoryScrollPosition: Float,
    onSelectedCategoryChange: (String) -> Unit,
    onChangeCategoryScrollPosition: (Float) -> Unit,
    selectedCategory: FoodCategory?,
    onToggleTheme: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
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
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
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

                ConstraintLayout(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    IconButton(onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu) {
                            this.end.linkTo(this.parent.end)
                            this.top.linkTo(this.parent.top)
                            this.bottom.linkTo(this.parent.bottom)
                    }) {
                        Icon(Icons.Filled.Palette)
                    }
                }
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