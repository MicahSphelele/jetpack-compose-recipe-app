package com.recipeapp.domain.util

interface DomainMapper<T, D> {

    fun mapToDomainModel(entity: T): D

    fun mapFromDomainModel(domainModel: D): T
}