package com.example.mytestapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object RetroFitCall {

    lateinit var retrofit: Retrofit
    private val BASE_URL = "https://randomuser.me/"
    private val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val okHttpClient by lazy { clientBuilder() }

    fun retroFitCall() {

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun retroFitWeatherCall() {

        retrofit = Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun clientBuilder(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

}