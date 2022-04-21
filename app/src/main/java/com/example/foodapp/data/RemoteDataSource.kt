package com.example.foodapp.data

import com.example.foodapp.data.network.FoodRecipesApi
import com.example.foodapp.models.FoodRecipes
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipe(queries: Map<String, String>):Response<FoodRecipes>{
        return foodRecipesApi.getRecipe(queries)
    }
}