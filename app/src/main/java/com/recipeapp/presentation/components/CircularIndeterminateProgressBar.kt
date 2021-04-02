package com.recipeapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable

fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
    }
    /*if (isDisplayed) {

        WithConstraints(modifier = Modifier.fillMaxSize()) {
            val constraints = if (this.minWidth < 600.dp) { //Portrait Mode
                decoupledConstraints(0.3f)
            } else {
                decoupledConstraints(0.7f)
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize(),
                constraintSet = constraints
            ) {

                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.layoutId("progressBar"))

                Text(text = "Loading...",
                    style = TextStyle(
                        color = Color.Black, fontSize = TextUnit.Companion.Sp(15)
                    ), modifier = Modifier.layoutId("text")
                )
            }
        }

    }*/

}

/*private fun decoupledConstraints(verticalBias: Float): ConstraintSet {
    return ConstraintSet {
        val guideline = createGuidelineFromTop(verticalBias)
        val progressBar = createRefFor("progressBar")
        val text = createRefFor("text")

        constrain(progressBar) {
            this.top.linkTo(guideline)
            this.start.linkTo(this.parent.start)
            this.end.linkTo(this.parent.end)
        }
        constrain(text) {
            this.top.linkTo(progressBar.bottom)
            this.start.linkTo(parent.start)
            this.end.linkTo(parent.end)
        }
    }
}*/