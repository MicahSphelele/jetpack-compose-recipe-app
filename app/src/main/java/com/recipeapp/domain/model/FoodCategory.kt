package com.recipeapp.domain.model

enum class FoodCategory(val value:String) {
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut")
}

fun getAllFoodCategories() : List<FoodCategory> = listOf(
    FoodCategory.CHICKEN,FoodCategory.BEEF,
    FoodCategory.SOUP,FoodCategory.DESSERT,
    FoodCategory.VEGETARIAN,FoodCategory.MILK,
    FoodCategory.VEGAN,FoodCategory.PIZZA,
    FoodCategory.DONUT)

fun getFoodCategory(value: String) : FoodCategory? =
    FoodCategory.values().
    associateBy(FoodCategory::value)[value]