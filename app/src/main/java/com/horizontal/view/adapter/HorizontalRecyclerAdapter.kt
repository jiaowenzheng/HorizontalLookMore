package com.horizontal.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.horizontal.view.R

class HorizontalRecyclerAdapter constructor(context: Context,private val data: ArrayList<String>?,private val requestManager: RequestManager) :
        RecyclerView.Adapter<HorizontalRecyclerItemViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalRecyclerItemViewHolder {
        return HorizontalRecyclerItemViewHolder(inflater.inflate(R.layout.item_recycler_view, parent, false))
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HorizontalRecyclerItemViewHolder, position: Int) {
        val url = data?.get(position) ?:""

        val requestOptions = RequestOptions()
        requestOptions.centerCrop()
        requestManager.load(url).apply(requestOptions).into(holder.cover)
    }
}