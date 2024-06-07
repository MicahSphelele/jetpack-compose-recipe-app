package com.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.recipeapp.R
import com.recipeapp.domain.model.Recipe
import com.recipeapp.util.loadPicture

@Composable
fun RecipeCard(recipe: Recipe,onClick:() -> Unit){
    
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp, start = 6.dp, end = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            recipe.featuredImage?.let { url ->

                val image = loadPicture(url = url, default = R.drawable.empty_plate).value

                image?.let {

                    Image(
                        bitmap = image.asImageBitmap(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(225.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Recipe Card Image")
                }
            }

            recipe.title?.let { title ->
                
                Row(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()){

                    Text(text = title, modifier = Modifier
                        .fillMaxWidth(0.85f),
                        style = MaterialTheme.typography.headlineMedium)

                    Text(text = recipe.rating.toString(),
                        textAlign = TextAlign.Center,color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .wrapContentSize(Alignment.Center)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}