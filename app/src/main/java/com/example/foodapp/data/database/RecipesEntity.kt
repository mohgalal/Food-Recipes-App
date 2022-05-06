package com.example.foodapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodapp.models.FoodRecipes
import com.example.foodapp.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipes: FoodRecipes
) {
    // Here I said that if fetch new data replace the old data, without move the cursor and keep the old data
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0



}