package com.recipeapp.network.responses

import com.google.gson.annotations.SerializedName
import com.recipeapp.network.model.RecipeDto

data class RecipeSearchResponse(
                                @SerializedName("count")
                                val count: Int,
                                @SerializedName("results")
                                val recipes: List<RecipeDto>)