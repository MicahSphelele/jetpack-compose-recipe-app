@file:OptIn(ExperimentalMaterial3Api::class)

package com.recipeapp.presentation.components

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable

fun AppAlertDialog(
    activity: Activity,
    title: String,
    message: String,
    buttonText: String,
    isShowing: MutableState<Boolean>
) {
    if (isShowing.value) {
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text(text = title, style = MaterialTheme.typography.headlineSmall)
            },
            text = {
                Text(text = message)
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)

                ) {
                    Button(
                        onClick = {
                            isShowing.value = false
                            activity.finish()
                        }
                    ) {
                        Text(buttonText)
                    }
                }
            }
        )
    }
}
