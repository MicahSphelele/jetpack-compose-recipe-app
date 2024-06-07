package com.recipeapp.presentation.components.util

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController(private val scope: CoroutineScope) {

    private var snackbarJob: Job? = null

    fun getScope() = scope

    init {
        cancelActiveJob()
    }

    fun showSnackbar(snackbarHostState: SnackbarHostState, message: String, actionLabel: String) {

        if (snackbarJob == null) {
            snackbarJob = scope.launch {
                snackbarHostState.showSnackbar(message, actionLabel)
                cancelActiveJob()
            }

            return
        }
        cancelActiveJob()
        snackbarJob = scope.launch {
            snackbarHostState.showSnackbar(message, actionLabel)
            cancelActiveJob()
        }

    }

    private fun cancelActiveJob() {
        snackbarJob?.let { job ->
            job.cancel()
            snackbarJob = Job()
        }
    }
}