package com.example.mytestapplication.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mytestapplication.R
import com.example.mytestapplication.Utils.AppConstants
import com.example.mytestapplication.Utils.CustomProgressDialog
import com.example.mytestapplication.database.model.WeatherModel
import com.github.matteobattilana.weather.PrecipType
import kotlinx.android.synthetic.main.weather_fragment.*
import java.util.*

private const val isFrom = "isFrom"
private const val LATITUDE = "latitude"
private const val LONGITUDE = "longitude"

class WeatherFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(fromActivity: String?, latitude: String?, longitude: String?) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(isFrom, fromActivity)
                    putString(LATITUDE, latitude)
                    putString(LONGITUDE, longitude)
                }
            }
    }

    private lateinit var locationManager: LocationManager
    private lateinit var viewModel: WeatherViewModel
    lateinit var weather: PrecipType
    lateinit var weatherText: String
    private var number = 0
    private var latitude: String? = null
    private var longitude: String? = null
    private var fromActivity: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        // TODO: Use the ViewModel
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        arguments?.let {
            fromActivity = it.getString(isFrom)
            latitude = it.getString(LATITUDE)
            longitude = it.getString(LONGITUDE)
        }

        if (fromActivity == "details") {
            viewModel.getWeatherApiCall(
                requireContext(),
                latitude?.toDouble()!!,
                longitude?.toDouble()!!
            )
        } else {

            if (!hasPermission(AppConstants.ALL_PERMISSIONS)) {
                requestPermission(
                    AppConstants.ALL_PERMISSIONS,
                    AppConstants.ALL_REQUEST_CODE
                )
            } else {
                getLocationFromManager()
            }

        }

        viewModel.weatherData.observe(viewLifecycleOwner, Observer {
            CustomProgressDialog.dismissProgress()
            main.visibility = View.VISIBLE
            changeWeather(it)
        })
    }

    private fun requestPermission(permissionsList: Array<String>, requestCode: Int) {
        val permissionNeeded: MutableList<String> = ArrayList()
        for (permission in permissionsList) {
            if (ActivityCompat.checkSelfPermission(requireActivity(), permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionNeeded.add(permission)
            }
        }
        if (permissionNeeded.size > 0) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionNeeded.toTypedArray(), requestCode
            )
        }
    }


    private fun hasPermission(permissionsList: Array<String>): Boolean {
        for (permission in permissionsList) {
            if (ActivityCompat.checkSelfPermission(requireActivity(), permission.toString())
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    @SuppressLint("MissingPermission")
    fun getLocationFromManager() {
        val lastKnownLocation =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        CustomProgressDialog.loadingProgress(requireActivity())
        main.visibility = View.GONE

        lastKnownLocation?.let {
            viewModel.getWeatherApiCall(
                requireContext(),
                it.latitude,
                it.longitude
            )
        }
    }

    private fun changeWeather(weatherModel: WeatherModel) {

        var weatherSpeed = 0
        var weatherParticles = 0f

        // Cycle through the weather cycles
        if (number < 2) {
            ++number
        } else {
            number = 0
        }

        val temp = AppConstants.kelvinToCelcius(weatherModel.main?.temp!!)
        //Randomly select a weather
        when {
            (temp > 28) -> {
                weather = PrecipType.CUSTOM
                weatherText = "Clear"
                Glide.with(requireContext()).load(R.drawable.ic_sunny).into(weatherImg)
            }
            (temp <= 9) -> {
                weather = PrecipType.SNOW
                weatherText = "Snow"
                weatherParticles = 10f
                weatherSpeed = 200
                Glide.with(requireContext()).load(R.drawable.ic_snow).into(weatherImg)
            }
            (temp in 10..20) -> {
                weather = PrecipType.RAIN
                weatherText = "Rain"
                weatherParticles = 60f
                weatherSpeed = 600
                Glide.with(requireContext()).load(R.drawable.ic_rain).into(weatherImg)
            }
            (temp in 21..28) -> {
                weather = PrecipType.CUSTOM
                weatherText = "Partially Clouded"
                Glide.with(requireContext()).load(R.drawable.ic_sun).into(weatherImg)
            }
            else -> {
                weather = PrecipType.CUSTOM
                weatherText = weatherModel.weather?.get(0)?.description.toString()
                Glide.with(requireContext()).load(R.drawable.ic_sun).into(weatherImg)
            }
        }

        // Set the weather text to the current weather
        tv_temp.text = "Temperature : ${AppConstants.kelvinToCelcius(weatherModel.main?.temp!!)} C"
        tv_city_name.text = "City : ${weatherModel.name}"
        tv_weather_status.text = "Weather : $weatherText"
        tv_feels.text =
            "Feels Like : ${AppConstants.kelvinToCelcius(weatherModel.main?.feelsLike!!)} C"

        //Update animation UI for weather
        weather_view.apply {
            setWeatherData(weather)
            speed = weatherSpeed // How fast
            emissionRate = weatherParticles // How many particles
            angle = 0 // The angle of the fall
            fadeOutPercent = .75f // When to fade out (0.0f-1.0f)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppConstants.ALL_REQUEST_CODE) {
            val permissionResults: MutableList<Int> = ArrayList()
            for (grantResult in grantResults) {
                permissionResults.add(grantResult)
            }
            if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


}