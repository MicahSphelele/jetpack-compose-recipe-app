package com.recipeapp.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingRecipeDetailShimmer(
    cardHeight: Dp,
    padding: Dp = 16.dp
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val cardWidthPx = with(LocalDensity.current) { (maxWidth - (padding*2)).toPx() }
        val cardHeightPx = with(LocalDensity.current) { (cardHeight - padding).toPx() }
        val gradientWidth: Float = (0.2f * cardHeightPx)

        val infiniteTransition = rememberInfiniteTransition(label = "Shimmer Transition")

        val xCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardWidthPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            ), label = "Shimmer X"
        )

        val yCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardHeightPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            ), label = "Shimmer Y"
        )

        val colors = listOf(
            Color.LightGray.copy(alpha = .9f),
            Color.LightGray.copy(alpha = .3f),
            Color.LightGray.copy(alpha = .9f),
        )

        LazyColumn {
            item {
                ShimmerRecipeDetailsCard(
                    colors = colors,
                    cardHeight = cardHeight ,
                    padding = padding,
                    xShimmer = xCardShimmer.value,
                    yShimmer = yCardShimmer.value,
                    gradientWidth =  gradientWidth
                )
            }
        }
    }
}



@Composable
private fun ShimmerRecipeDetailsCard(
    colors: List<Color>,
    cardHeight: Dp,
    padding: Dp,
    xShimmer: Float,
    yShimmer: Float,
    gradientWidth: Float
) {

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(xShimmer - gradientWidth, yShimmer - gradientWidth),
        end = Offset(xShimmer, yShimmer)
    )

    Column(modifier = Modifier
        .padding(padding)) {
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(cardHeight)
                    .background(brush = brush)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(cardHeight / 10)
                    .background(brush = brush)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(cardHeight / 10)
                    .background(brush = brush)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(cardHeight / 10)
                    .background(brush = brush)
            )
        }
    }
}