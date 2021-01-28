package com.recipeapp.network.model

import com.recipeapp.domain.model.Recipe
import com.recipeapp.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto,Recipe> {

    override fun mapToDomainModel(entity: RecipeDto): Recipe {
        return Recipe(id = entity.pk,
            title = entity.title,
            publisher = entity.publisher,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            sourceUrl = entity.sourceUrl,
            description = entity.description,
            cookingInstructions = entity.cookingInstructions,
            ingredients = entity.ingredients?: listOf(),
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated)
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(pk = domainModel.id,
            title = domainModel.title,
            publisher = domainModel.publisher,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl,
            description = domainModel.description,
            cookingInstructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated)
    }

    fun toDomainList(initial: List<RecipeDto>) : List<Recipe>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>) : List<RecipeDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}