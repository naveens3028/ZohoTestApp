package com.example.mytestapplication.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestapplication.Utils.AppConstants
import com.example.mytestapplication.database.model.WeatherModel
import com.example.mytestapplication.network.ApiInterface
import com.example.mytestapplication.network.RetroFitCall
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var weatherData: MutableLiveData<WeatherModel> = MutableLiveData()

    fun getWeatherApiCall(context: Context, latitude: Double, longitude: Double) {
        RetroFitCall.retroFitWeatherCall()
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val hashmap = HashMap<String, String>()
        hashmap.put("lat", latitude.toString())
        hashmap.put("lon", longitude.toString())
        hashmap.put("appid", AppConstants.API_KEY)
        viewModelScope.launch {
            val response = service.getWaether(hashmap)
            if (response.isSuccessful) {
                println(response.body()?.name)
                weatherData.postValue(response.body())
            } else {
                Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
            }
        }
    }

}