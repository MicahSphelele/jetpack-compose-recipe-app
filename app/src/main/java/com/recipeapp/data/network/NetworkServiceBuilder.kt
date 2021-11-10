package com.recipeapp.data.network

import com.google.gson.GsonBuilder
import com.recipeapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServiceBuilder {

    const val TOKEN = BuildConfig.RECIPE_APP_API_TOKEN
    const val NAMED_TOKEN = "token"

    fun <T> buildService(service: Class<T>): T{

        return Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient.Builder().build())
            .build().create(service)
    }
}