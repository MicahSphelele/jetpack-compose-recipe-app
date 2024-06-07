package com.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun RecipeDetailView(
    contentPadding: PaddingValues,
    recipe: Recipe
) {

    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .fillMaxSize()
    ) {
        item {
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
                        .requiredHeight(260.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                recipe.title?.let { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Text(
                            text = title, modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = recipe.rating.toString(),
                            textAlign = TextAlign.Center, color = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                                .wrapContentSize(Alignment.Center)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    recipe.publisher?.let { publisher ->

                        val dateUpdated = recipe.dateUpdated

                        Text(
                            text = if (!dateUpdated.isNullOrEmpty()) {
                                "Updated $dateUpdated by $publisher"
                            } else {
                                "Updated by $publisher"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    recipe.ingredients.forEach { ingredient ->
                        Text(
                            text = ingredient,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp,bottom = 8.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}