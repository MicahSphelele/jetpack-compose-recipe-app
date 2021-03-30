package com.recipeapp.presentation.components

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
    state: MutableState<Boolean>
) {
    if (state.value) {
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text(text = title,style = MaterialTheme.typography.h6)
            },
            text = {
                Text(text = message)
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)

                ) {
                    Button(
                        onClick = {
                            state.value = false
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
