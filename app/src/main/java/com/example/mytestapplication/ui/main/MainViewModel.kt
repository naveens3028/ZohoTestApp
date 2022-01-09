package com.example.mytestapplication.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytestapplication.Utils.AppConstants.API_KEY
import com.example.mytestapplication.database.AppDataBase
import com.example.mytestapplication.database.model.DataModel
import com.example.mytestapplication.database.model.WeatherModel
import com.example.mytestapplication.network.ApiInterface
import com.example.mytestapplication.network.RetroFitCall
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val isDataCallCompleted: MutableLiveData<DataModel>? =  MutableLiveData<DataModel>()

    fun getApiCall(context: Context, db: AppDataBase?) {
        RetroFitCall.retroFitCall()
        val service = RetroFitCall.retrofit.create(ApiInterface::class.java)
        val hashmap = HashMap<String, Int>()
        hashmap.put("results", 200)
        viewModelScope.launch {
            val response = service.getData(hashmap)
            if (response.isSuccessful){
                println("oberver2")

                var auditList : DataModel = response.body()!!
                db?.dataModelDao?.addAvg(auditList)
                isDataCallCompleted?.postValue(db?.dataModelDao?.getAll())
            }else{
                Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
            }

        }
    }

}