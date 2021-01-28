package com.recipeapp.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceBuilder {

    const val TOKEN = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    const val NAMED_TOKEN = "token"

    fun <T> buildService(service: Class<T>): T{

        return Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient.Builder().build())
            .build().create(service)
    }
}