package com.recipeapp.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.recipeapp.R

const val DEFAULT_RECIPE_IMAGE = R.drawable.empty_plate

@Composable
fun loadPicture(url: String, @DrawableRes default: Int) : MutableState<Bitmap?> {

    val bitmapState: MutableState<Bitmap?> = rememberSaveable { mutableStateOf(null) }

    Glide.with(LocalContext.current).
            asBitmap()
        .load(default)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })

    Glide.with(LocalContext.current).
    asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(url: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = url
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })

    return bitmapState
}

@Composable
fun loadPicture(@DrawableRes drawable: Int) : MutableState<Bitmap?> {
    val bitmapState: MutableState<Bitmap?> = rememberSaveable { mutableStateOf(null) }

    Glide.with(LocalContext.current).
    asBitmap()
        .load(drawable)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })
    return bitmapState
}