package com.example.mytestapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapplication.ui.main.MainFragment
import com.example.mytestapplication.ui.main.WeatherFragment

class MainActivity : AppCompatActivity() {

    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val phaseFrom = intent.getStringExtra("phase")
        val lat = intent.getStringExtra("lat")
        val lon = intent.getStringExtra("lon")
        val city = intent.getStringExtra("city")

        if (savedInstanceState == null) {
            when (phaseFrom) {
                "phase1" -> {
                    title = "Users List"
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow()
                }
                "phase2" -> {
                    title = "Weather Details"

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, WeatherFragment.newInstance("main", "", "",""))
                        .commitNow()
                }
                else -> {
                    title = "Weather Details"

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, WeatherFragment.newInstance("details", lat, lon, city))
                        .commitNow()
                }
            }
        }

        setTitle(title)
    }
}