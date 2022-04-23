package com.example.foodapp.data.network

import com.example.foodapp.models.FoodRecipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.time.temporal.TemporalQueries

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipe(
        @QueryMap queries: Map<String, String>
    ):Response<FoodRecipes>




}