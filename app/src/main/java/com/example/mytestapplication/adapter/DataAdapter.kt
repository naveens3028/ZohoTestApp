package com.example.mytestapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapplication.R
import com.example.mytestapplication.database.model.Result
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.list_recycler_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DataAdapter(var myData: List<Result>?, var listener: OnDataClickListener) : RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    private val item: Int = 0
    private val loading: Int = 1
    private var isLoadingAdded: Boolean = false

    fun updateList(results: List<Result>?) {
        myData = results
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return if (viewType == item) {
            val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_recycler_item, parent, false)
            return MyViewHolder(inflater)
        } else {
            val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            return MyViewHolder(inflater)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            item
        } else {
            if (position == myData?.size!! - 1) {
                loading
            } else {
                item
            }
        }
    }

    override fun getItemCount(): Int {
        return myData?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (getItemViewType(position) == item) {
            val data = myData?.get(position)
            holder.itemView.nameTxt.text = (position+1).toString() + ".  " + data?.name?.first
            holder.itemView.addressTxt.text = "Email : "+data?.email
            holder.itemView.popTxt.text =  "Gender : "+data?.gender

            holder.itemView.setOnClickListener {
                data?.let { it1 -> listener.onDataClicked(it1.picture?.large!!,
                    it1.name?.first.toString(), it1.email.toString(),
                    it1.location?.coordinates?.latitude!!,
                    it1.location?.coordinates?.longitude!!,
                    it1.location!!.country!!
                ) }
            }
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                holder.itemView.loadmore_progress.visibility = View.VISIBLE
                delay(1000)
                holder.itemView.loadmore_progress.visibility = View.GONE
            }
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}

interface OnDataClickListener{
    fun onDataClicked(image : String, name: String, desc: String, lat: String, lon: String, country: String )
}