package com.example.mytestapplication.network

import com.example.mytestapplication.database.model.DataModel
import com.example.mytestapplication.database.model.WeatherModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("api/")
    suspend fun getData(@QueryMap results : HashMap<String, Int>): Response<DataModel>

    @GET("weather")
    suspend fun getWeather(@QueryMap results: HashMap<String, String>): Response<WeatherModel>


}