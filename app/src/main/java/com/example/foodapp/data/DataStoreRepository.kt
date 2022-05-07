package com.example.foodapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodapp.util.Constants.Companion.PREFERENCE_DIET_TYPE
import com.example.foodapp.util.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.example.foodapp.util.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.example.foodapp.util.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import com.example.foodapp.util.Constants.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {


    private object PreferenceKeys {

        var selectedMealType = stringPreferencesKey(PREFERENCE_MEAL_TYPE)
        var selectedMealTypeId = intPreferencesKey(PREFERENCE_MEAL_TYPE_ID)
        var selectedDietType = stringPreferencesKey(PREFERENCE_DIET_TYPE)
        var selectedDietTypeId = intPreferencesKey(PREFERENCE_DIET_TYPE_ID)

    }

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)
    private val appContext = context.applicationContext


    // These parameters I'll take it from bottomSheet, when the user click on it.
    // And save them in
    suspend fun saveMealAndDietType (mealType:String, mealTypeId:Int, dietType:String, dietTypeId:Int){

        appContext.dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId

        }

    }

    val readMealAndDietType : Flow<MealAndDietType> = appContext.dataStore.data
        .catch { exception->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        } // Here I set the values in variables to pass them to model
        .map { preferences->
            var selectedMealType = preferences[PreferenceKeys.selectedMealType] ?:DEFAULT_MEAL_TYPE
            var selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            var selectedDietType = preferences[PreferenceKeys.selectedDietType] ?:DEFAULT_DIET_TYPE
            var selectedDietTypeId =preferences[PreferenceKeys.selectedDietTypeId] ?: 0

            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

}

data class MealAndDietType (
    val selectedMealType : String,
    val selectedMealTypeId : Int,
    val selectedDietType : String,
    val selectedDietTypeId : Int
        )