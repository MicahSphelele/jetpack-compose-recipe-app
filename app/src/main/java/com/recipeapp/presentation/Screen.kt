package com.recipeapp.presentation

sealed class Screen(val route: String) {
    object RecipeListScreen: Screen("recipe_list_screen")
    object RecipeDetailsScreen: Screen("recipe_details_screen")
}
