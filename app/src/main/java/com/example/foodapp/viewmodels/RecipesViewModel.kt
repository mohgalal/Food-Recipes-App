package com.example.foodapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.DataStoreRepository
import com.example.foodapp.data.MealAndDietType
import com.example.foodapp.util.Constants
import com.example.foodapp.util.Constants.Companion.API_KEY
import com.example.foodapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodapp.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foodapp.util.Constants.Companion.QUERY_ADD_RECIPE_INFO
import com.example.foodapp.util.Constants.Companion.QUERY_API_KEY
import com.example.foodapp.util.Constants.Companion.QUERY_DIET
import com.example.foodapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodapp.util.Constants.Companion.QUERY_NUMBRE
import com.example.foodapp.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    var mealType = DEFAULT_MEAL_TYPE
    var dietType = DEFAULT_DIET_TYPE

    var readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType:String, mealTypeId:Int, dietType:String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType,mealTypeId,dietType,dietTypeId)
        }


     fun applyQuery(): HashMap<String, String>{
        val query : HashMap<String, String> = HashMap()

         viewModelScope.launch {
             readMealAndDietType.collect { values ->
                 mealType = values.selectedMealType
                 dietType = values.selectedDietType
             }
         }

        query[QUERY_NUMBRE] = DEFAULT_RECIPES_NUMBER
        query[QUERY_API_KEY] = API_KEY
        query[QUERY_TYPE] = DEFAULT_MEAL_TYPE
        query[QUERY_DIET] = DEFAULT_DIET_TYPE
        query[QUERY_ADD_RECIPE_INFO] = "true"
        query[QUERY_FILL_INGREDIENTS] = "true"

        return query
    }
}