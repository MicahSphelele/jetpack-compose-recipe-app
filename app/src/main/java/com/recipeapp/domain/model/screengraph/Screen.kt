package com.recipeapp.domain.model.screengraph

sealed class Screen(val route: String) {
    object RecipeListScreen: Screen("recipe_list_screen")
    object RecipeDetailsScreen: Screen("recipe_details_screen")
}
