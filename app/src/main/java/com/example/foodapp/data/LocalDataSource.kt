package com.example.foodapp.data

import com.example.foodapp.data.database.RecipesDao
import com.example.foodapp.data.database.RecipesEntity
import com.example.foodapp.models.FoodRecipes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
){

    fun readRecipes():Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }


}