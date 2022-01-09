package com.example.mytestapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mytestapplication.ui.main.WeatherFragment
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    lateinit var latitude : String
    lateinit var longitude: String
    lateinit var country: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val image = intent.getStringExtra("data")
        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        latitude = intent.getStringExtra("lat")!!
        longitude = intent.getStringExtra("lon")!!
        country = intent.getStringExtra("country")!!

        Glide.with(this).load(image).into(img_details)
        nameTxt.text = name
        details_txt.text = desc
        country_txt.text = country

        weatherBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("phase", "details")
            intent.putExtra("lat", latitude)
            intent.putExtra("lon", longitude)
            intent.putExtra("city", country)
            startActivity(intent)
        }

        title = "Details Page"

    }

}