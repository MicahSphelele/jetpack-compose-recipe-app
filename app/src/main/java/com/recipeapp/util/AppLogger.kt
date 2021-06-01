package com.recipeapp.util

import android.util.Log

object AppLogger {

    private const val TAG = "RecipeApp"

    fun info(message: String) {
        Log.i(TAG, message)
    }

    fun warn(message: String) {
        Log.w(TAG, message)
    }

    fun error(message: String) {
        Log.e(TAG, message)
    }

    fun error(throwable: Throwable?) {
        Log.e(TAG, "Unknown error", throwable)
    }

    fun error(message: String,throwable: Throwable?) {
        Log.e(TAG, message, throwable)
    }
}