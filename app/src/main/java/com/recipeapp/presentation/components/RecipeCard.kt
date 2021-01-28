package com.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import com.recipeapp.R
import com.recipeapp.domain.model.Recipe
import com.recipeapp.util.DEFAULT_RECIPE_IMAGE
import com.recipeapp.util.loadPicture

@Composable
fun RecipeCard(recipe: Recipe,onClick:() -> Unit){
    
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp,start = 6.dp,end = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            recipe.featuredImage?.let { url ->

                val image = loadPicture(url = url, default = DEFAULT_RECIPE_IMAGE).value

                image?.let {

                    Image(
                        bitmap = image.asImageBitmap(),
                        modifier = Modifier.fillMaxWidth()
                            .preferredHeight(225.dp),
                        contentScale = ContentScale.Crop)
                }
            }

            recipe.title?.let { title ->
                Row(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 12.dp,bottom = 12.dp,start = 8.dp,end = 8.dp)
                    .fillMaxWidth()){

                    Text(text = title, modifier = Modifier
                        .fillMaxWidth(0.85f),
                        style = MaterialTheme.typography.h6)

                    Text(text = recipe.rating.toString(), modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.subtitle2)
                }
            }
        }
    }
}