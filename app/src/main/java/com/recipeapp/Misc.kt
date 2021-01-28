package com.recipeapp

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

var toggleState = mutableStateOf(false)

@Composable
fun HappyMeal(){
    ScrollableColumn(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0XFFf2f2f2))) {
        Image(
            bitmap = imageFromResource(res = AmbientContext.current.resources,
                resId = R.drawable.happy_meal_small),
            modifier = Modifier.height(300.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {

                Text(text = "Happy Meal",
                    style = TextStyle(
                        fontSize = TextUnit.Sp(26)
                    )
                )

//                    Row(modifier = Modifier
//                        .border(border = BorderStroke(width = 1.dp,color = Color.DarkGray))
//                        .padding(8.dp)
//                    ) {
                Text(text = "R 65.99",
                    style = TextStyle(
                        color = Color(0XFF85bb64),
                        fontSize = TextUnit.Sp(17)
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically))
                // }
            }

            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(text = "800 Calories",
                style = TextStyle(
                    fontSize = TextUnit.Sp(17)
                )
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))

            Button(onClick = {
                toggleState.value = !toggleState.value

            },modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text =  buttonText())

            }
        }
    }
}

@Composable
fun UseIt(){
    Column {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .border(border = BorderStroke(width = 1.dp,color = Color.Black)),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Item Number 1",
                modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(text = "Item Number 2",
                modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.padding(20.dp))

        Row(modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .border(border = BorderStroke(width = 1.dp,color = Color.Black))
            .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Item Number 2",
                modifier = Modifier.align(Alignment.CenterVertically))
        }
    }//End Parent

}

private fun buttonText() : String {
    return if(!toggleState.value)
        "Order Now"
    else
        "Done"
}