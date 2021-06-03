package com.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.recipeapp.domain.model.FoodCategory
import com.recipeapp.domain.model.getAllFoodCategories
import com.recipeapp.presentation.ui.recipe_list.RecipeListEvent

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onExecuteSearch: (RecipeListEvent) -> Unit,
    onSelectedCategoryChange: (String) -> Unit,
    selectedCategory: FoodCategory?,
    onToggleTheme: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

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
                TextField(value = query,
                    onValueChange = { value ->
                    onQueryChange(value)
                },
                modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp)
                        .background(color = MaterialTheme.colors.surface),
                label = {Text(text = "Search")} ,
                keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                keyboardActions = KeyboardActions(onDone = {
                        onExecuteSearch(RecipeListEvent.SearchEvent)
                        keyboardController?.hide()
                    }),
                leadingIcon = {Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon")},
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                )

                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    IconButton(onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu) {
                            this.end.linkTo(this.parent.end)
                            this.top.linkTo(this.parent.top)
                            this.bottom.linkTo(this.parent.bottom)
                    }) {
                        Icon(imageVector = Icons.Filled.Bedtime,
                            contentDescription = "Night/Light Mode Icon")
                    }
                }
            }

            val scrollState = rememberLazyListState()
            val categories = getAllFoodCategories()
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        bottom = 8.dp
                    ),
                state = scrollState
            ) {

                items(categories) {
                    FoodCategoryChip(
                        category = it.value,
                        onSelectedCategoryChange = { category ->
                            onSelectedCategoryChange(category)
                        }, isSelected = selectedCategory == it,
                        onExecuteSearch = {
                            onExecuteSearch(RecipeListEvent.SearchEvent)
                        })
                }

            }
        }
    }
}