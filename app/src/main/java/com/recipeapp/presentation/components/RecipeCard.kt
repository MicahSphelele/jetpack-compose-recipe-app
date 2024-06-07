package com.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.recipeapp.R
import com.recipeapp.domain.model.Recipe

@Composable
fun RecipeCard(recipe: Recipe,onClick:() -> Unit){
    
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp, start = 6.dp, end = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            recipe.featuredImage?.let { url ->
                AsyncImage(
                    model =
                    ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .allowHardware(false)
                        .error(R.drawable.empty_plate)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = "Recipe item image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .semantics { contentDescription = "ivRestaurantLogo" }
                        .fillMaxWidth()
                        .requiredHeight(225.dp)
                )
            }

            recipe.title?.let { title ->
                
                Row(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()){

                    Text(text = title, modifier = Modifier
                        .fillMaxWidth(0.85f),
                        style = MaterialTheme.typography.headlineSmall)

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