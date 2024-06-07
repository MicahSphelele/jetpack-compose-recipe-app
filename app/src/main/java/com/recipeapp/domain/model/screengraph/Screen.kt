package com.recipeapp.domain.model.screengraph

sealed class Screen(val route: String) {
    data object RecipeListScreen : Screen(route = "recipe_list_screen")
    data object RecipeDetailsScreen : Screen(route = "recipe_details_screen") {
        fun buildWithParams(id: Int) = "${route}/${id}"

        val routeWithParams: String
            get() = "$route/{recipeId}"
    }
}
