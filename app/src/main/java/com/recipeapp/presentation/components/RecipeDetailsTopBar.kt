package com.recipeapp.presentation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun RecipeDetailsTopBar(title: String = "", onBackArrowClick: () -> Unit) {
    
    TopAppBar(
        title =  {
            Text(text = title,
            color = Color.White,
            style = MaterialTheme.typography.h4)
        },
        navigationIcon = {
            IconButton(
                onClick = onBackArrowClick
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = "Back Icon Button")
            }
        })
}