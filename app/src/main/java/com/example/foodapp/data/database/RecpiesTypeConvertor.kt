package com.example.foodapp.data.database

import androidx.room.TypeConverter
import com.example.foodapp.models.FoodRecipes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RecpiesTypeConvertor {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipes: FoodRecipes):String {
        return gson.toJson(foodRecipes)
    }

    @TypeConverter
    fun stringToFoodRecipes(data : String):FoodRecipes{

        val listType = object : TypeToken<FoodRecipes>() {}.type
        return gson.fromJson(data,listType)
    }

}