package com.example.foodapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.foodapp.util.Constants
import com.example.foodapp.util.Constants.Companion.API_KEY
import com.example.foodapp.util.Constants.Companion.QUERY_ADD_RECIPE_INFO
import com.example.foodapp.util.Constants.Companion.QUERY_API_KEY
import com.example.foodapp.util.Constants.Companion.QUERY_DIET
import com.example.foodapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodapp.util.Constants.Companion.QUERY_NUMBRE
import com.example.foodapp.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel


class RecipesViewModel(
    application: Application
) : AndroidViewModel(application) {

     fun applyQuery(): HashMap<String, String>{
        val query : HashMap<String, String> = HashMap()

        query[QUERY_NUMBRE] = "50"
        query[QUERY_API_KEY] = API_KEY
        query[QUERY_TYPE] = "snack"
        query[QUERY_DIET] = "vegan"
        query[QUERY_ADD_RECIPE_INFO] = "true"
        query[QUERY_FILL_INGREDIENTS] = "true"

        return query
    }
}