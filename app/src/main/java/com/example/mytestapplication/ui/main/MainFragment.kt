package com.example.mytestapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapplication.DetailsActivity
import com.example.mytestapplication.R
import com.example.mytestapplication.adapter.DataAdapter
import com.example.mytestapplication.adapter.OnDataClickListener
import com.example.mytestapplication.database.AppDataBase
import com.example.mytestapplication.database.model.DataModel
import com.example.mytestapplication.database.model.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.widget.Toast

import android.widget.AdapterView

import android.widget.AdapterView.OnItemClickListener
import androidx.core.widget.doAfterTextChanged


class MainFragment : Fragment() , OnDataClickListener{

    companion object {
        fun newInstance() = MainFragment()
    }
    var currentPage = 0
    var listSize = 25
    private lateinit var dataBase: AppDataBase
    private lateinit var viewModel: MainViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var myAdapter: DataAdapter
    private var myList: List<Result>?=null
    private var nameList : ArrayList<String> =  ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        dataBase = AppDataBase.getInstance(requireContext())!!

        if (dataBase.dataModelDao.getAll() == null) {
            loadmore_progress1.visibility = View.VISIBLE
            data_recycler.visibility = View.GONE
            viewModel.getApiCall(requireContext(), dataBase)
        }else{
            callAdapter(dataBase.dataModelDao.getAll())
        }

        viewModel.isDataCallCompleted?.observe(viewLifecycleOwner, Observer {
            loadmore_progress1.visibility = View.GONE
            data_recycler.visibility = View.VISIBLE
            callAdapter(dataBase.dataModelDao.getAll())
        })

        data_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItemPosition: Int =
                        layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition == totalItemCount - 1
                    ) {
                        CoroutineScope(Dispatchers.Main).launch {
                            currentPage += 1
                            if (currentPage != 0) {
                                var myListLocal =
                                    dataBase.dataModelDao.getAll().results?.filterIndexed { index, result ->
                                        index <= (currentPage * listSize)
                                    }
                                nameList.clear()
                                myListLocal?.forEach {
                                    nameList.add(it.name?.first!!)
                                }
                                myList = myListLocal
                                callAutoCompleteAdapter()
                                delay(1000)
                                myAdapter.let {
                                    it.updateList(myListLocal)
                                }
                            }
                        }
                    }
                }
            }
        })

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        onCreate(savedInstanceState)
    }

    private fun callAdapter(myData: DataModel) {
        val filteredData = myData.results?.filterIndexed { index, result ->
            index <= 25
        }
        nameList.clear()
        filteredData?.forEach {
            nameList.add(it.name?.first!!)
        }
        callAutoCompleteAdapter()
        myList = filteredData
        currentPage = 1
        layoutManager = LinearLayoutManager(requireContext())
        data_recycler.layoutManager = layoutManager
        myAdapter = DataAdapter(filteredData, this)
        data_recycler.adapter = myAdapter
    }

    fun callAutoCompleteAdapter(){

        autoCompleteTxt.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                var filteredList = myList
                val finalList =
                    filteredList?.filter {it1->
                        it1.name?.first?.startsWith(it.toString(), true) == true
                    }
                myAdapter.updateList(finalList)
            }else{
                myAdapter.updateList(myList)
            }
        }
    }


    override fun onDataClicked(image: String, name: String, desc: String,  lat: String, lon: String, country: String) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("data", image)
        intent.putExtra("name", name)
        intent.putExtra("desc", desc)
        intent.putExtra("lat", lat)
        intent.putExtra("lon", lon)
        intent.putExtra("country", country)
        context?.startActivity(intent)
    }

}