package com.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.recipeapp.domain.model.Recipe
import com.recipeapp.util.DEFAULT_RECIPE_IMAGE
import com.recipeapp.util.loadPicture

@Composable
fun RecipeDetailView(recipe: Recipe) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            recipe.featuredImage?.let { url ->
                val image= loadPicture(url = url, default = DEFAULT_RECIPE_IMAGE).value

                image?.let {
                    Image(
                        bitmap = image.asImageBitmap(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(260.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Detailed Recipe Image")
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ) {
                recipe.title?.let { title ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {

                        Text(text = title, modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3)

                        Text(text = recipe.rating.toString(),
                            textAlign = TextAlign.Center,color = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = CircleShape
                                )
                                .wrapContentSize(Alignment.Center)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h5
                        )
                    }
                    recipe.publisher?.let { publisher ->

                        val dateUpdated = recipe.dateUpdated

                        Text(text = if (!dateUpdated.isNullOrEmpty()) {
                            "Updated $dateUpdated by $publisher"
                        }else {
                            "Updated by $publisher"
                        },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.caption)
                    }

                    for (ingredient in recipe.ingredients) {
                        Text(
                            text = ingredient,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}