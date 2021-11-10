package com.recipeapp.domain.model

import com.google.gson.annotations.SerializedName
import com.recipeapp.domain.model.RecipeDto

data class RecipeSearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val recipes: List<RecipeDto>
)