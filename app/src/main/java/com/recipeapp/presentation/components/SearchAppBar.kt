package com.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.recipeapp.domain.model.FoodCategory
import com.recipeapp.domain.model.enums.UiState
import com.recipeapp.domain.model.getAllFoodCategories
import com.recipeapp.presentation.components.util.toast
import com.recipeapp.presentation.ui.recipe_list.RecipeListEvent

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onExecuteSearch: (RecipeListEvent) -> Unit,
    onSelectedCategoryChange: (String) -> Unit,
    selectedCategory: FoodCategory?,
    onChangeUiMode: (UiState) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val dropDownMenuExpanded = remember { mutableStateOf(false) }

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
                    onValueChange = { value ->
                        onQueryChange(value)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp)
                        .background(color = MaterialTheme.colors.surface),
                    label = { Text(text = "Search") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        onExecuteSearch(RecipeListEvent.SearchEvent)
                        keyboardController?.hide()
                    }),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                )

                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    IconButton(onClick = {
                        dropDownMenuExpanded.value = !dropDownMenuExpanded.value
                    },
                        modifier = Modifier.constrainAs(menu) {
                            this.end.linkTo(this.parent.end)
                            this.top.linkTo(this.parent.top)
                            this.bottom.linkTo(this.parent.bottom)
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Bedtime,
                            contentDescription = "Night/Light Mode Icon"
                        )
                    }

                    ContextMenu(
                        expanded = dropDownMenuExpanded,
                        onChangeUiMode = onChangeUiMode
                    )
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

@Composable
fun ContextMenu(
    expanded: MutableState<Boolean>,
    onChangeUiMode: (UiState) -> Unit
) {

    val context = LocalContext.current
    val isSystemInDarkTheme = isSystemInDarkTheme()

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        DropdownMenuItem(onClick = {
            context.toast("Using Light")
            expanded.value = false
            onChangeUiMode(UiState.LIGHT)
        }) {
            Text("Light")
        }

        Divider()

        DropdownMenuItem(onClick = {
            context.toast("Using Dark")
            expanded.value = false
            onChangeUiMode(UiState.DARK)
        }) {
            Text("Dark")
        }

        Divider()

        DropdownMenuItem(onClick = {
            context.toast("Using System")
            expanded.value = false
            onChangeUiMode(UiState.SYSTEM)
        }) {
            Text("System Default")
        }
    }
}
