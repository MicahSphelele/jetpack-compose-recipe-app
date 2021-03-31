package com.recipeapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.TransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.recipeapp.R
import com.recipeapp.presentation.components.HeartAnimation.HeartButtonState.ACTIVE
import com.recipeapp.presentation.components.HeartAnimation.HeartButtonState.IDLE
import com.recipeapp.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun AnimatedHeartButton(modifier: Modifier,
                    buttonState: MutableState<HeartAnimation.HeartButtonState>,
                    onToggle: () -> Unit) {

    val toState = if (buttonState.value == IDLE){
        ACTIVE
    }else{
        IDLE
    }

    val state = transition(
        definition = HeartAnimation.heartTransitionDefinition,
        toState = toState,
        initState = buttonState.value)

    HeartButton(
        modifier = modifier,
        buttonState = buttonState,
        state = state,
        onToggle = onToggle)


}

@ExperimentalCoroutinesApi
@Composable
private fun HeartButton(
    modifier: Modifier,
    buttonState: MutableState<HeartAnimation.HeartButtonState>,
    state: TransitionState,
    onToggle: () -> Unit,
){

    if (buttonState.value == ACTIVE){
        loadPicture(drawable = R.drawable.heart_red).value?.let {
            Image(bitmap = it.asImageBitmap(),
            modifier = modifier
                .clickable(
                    onClick = onToggle,
                    indication = null
                )
                .width(state[HeartAnimation.heartSize])
                .height(state[HeartAnimation.heartSize]))

        }
    }else {
        loadPicture(drawable = R.drawable.heart_grey).value?.let {
            Image(bitmap = it.asImageBitmap(),
                modifier = modifier
                    .clickable(
                        onClick = onToggle,
                        indication = null
                    )
                    .width(state[HeartAnimation.heartSize])
                    .height(state[HeartAnimation.heartSize]))

        }
    }

}

object HeartAnimation {
    enum class HeartButtonState {
        IDLE, ACTIVE
    }

    private val idleIconSize = 50.dp
    private val expandedIconSize  = 80.dp

    val heartColor = ColorPropKey(label = "heartColor")
    val heartSize = DpPropKey(label = "heartSize")


    @SuppressLint("Range")
    val heartTransitionDefinition = transitionDefinition<HeartButtonState> {
        state(IDLE) {
            this[heartColor] = Color.LightGray
            this[heartSize] = idleIconSize
        }
        state(ACTIVE) {
            this[heartColor] = Color.Red
            this[heartSize] = idleIconSize
        }

        transition(IDLE to ACTIVE) {
            heartColor using tween(durationMillis = 500)

            heartSize using keyframes {
                durationMillis = 500
                expandedIconSize at 100
                idleIconSize at 200
            }

            heartSize using keyframes {
                durationMillis = 500
            }
        }

        transition(ACTIVE to IDLE) {
            heartColor using tween(durationMillis = 500)

            heartSize using keyframes {
                durationMillis = 500
                expandedIconSize at 100
                idleIconSize at 200
            }
        }
    }

}

