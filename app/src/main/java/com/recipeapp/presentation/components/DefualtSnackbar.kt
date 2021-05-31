package com.recipeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun DefaultSnackbar(
    snackbarHostSate: SnackbarHostState,
    modifier: Modifier,
    onDismiss: () -> Unit
) {
    SnackbarHost(hostState = snackbarHostSate,
        snackbar = { data ->
        Snackbar(modifier = Modifier.padding(16.dp), text = {
            Text(text = data.message, style = MaterialTheme.typography.body2, color = MaterialTheme.colors.surface)
        }, action = {
            data.actionLabel?.let { label ->
                TextButton(onClick = { onDismiss() }) {
                    Text(text = label, style = MaterialTheme.typography.body2, color = MaterialTheme.colors.surface)
                }
            }
        })
    }, modifier = modifier)

}