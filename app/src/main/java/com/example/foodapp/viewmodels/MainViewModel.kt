package com.example.foodapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.Repository
import com.example.foodapp.models.FoodRecipes
import com.example.foodapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

// with @ViewModelInject I don't neet to ViewModelFactory
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipes>> = MutableLiveData()


    fun getRecipe(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {

        recipesResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                var response = repository.remote.getRecipe(queries)
                recipesResponse.value = handelFoodRecipesResponse(response)
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes Not Found")
            }


        } else {

            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }

    }

    private fun handelFoodRecipesResponse(response: Response<FoodRecipes>): NetworkResult<FoodRecipes>? {
        // I get this information of API page
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }
            response.body()!!.results.isEmpty() -> {
                return NetworkResult.Error("Recipes Not Found")
            }
            response.isSuccessful -> {
                var data = response.body()
                return NetworkResult.Success(data)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }

        }

    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}