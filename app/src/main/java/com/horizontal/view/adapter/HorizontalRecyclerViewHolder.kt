package com.horizontal.view.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.horizontal.view.R

class HorizontalRecyclerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val cover = itemView.findViewById<ImageView>(R.id.iv_cover)

}