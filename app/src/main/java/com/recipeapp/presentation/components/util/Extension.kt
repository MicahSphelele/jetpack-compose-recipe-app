package com.recipeapp.presentation.components.util

import android.content.Context
import android.widget.Toast

// extension function to show toast message
fun Context.toast(message:String = ""){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
