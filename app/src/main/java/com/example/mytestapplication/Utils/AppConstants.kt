package com.example.mytestapplication.Utils

import android.Manifest
import kotlin.math.roundToInt

object AppConstants {

    const val API_KEY = "9ad59d584acfb8e3ffd06b2ab92583a5"
    const val ALL_REQUEST_CODE = 101

    val ALL_PERMISSIONS = arrayOf<String>(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )


    fun kelvinToCelcius(kelvin: Double) : Int {
        return (kelvin - 273.15).roundToInt()
    }

}