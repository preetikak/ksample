package com.preetika.katerassignment.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.preetika.katerassignment.model.Data

class FavoriteListAdapter() : RecyclerView.Adapter<GIfViewHolder>() {
    lateinit var gifList : List<Data>
    init {
        gifList  = ArrayList<Data>()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GIfViewHolder {
        return GIfViewHolder.create(parent,true)
    }

    override fun onBindViewHolder(holder: GIfViewHolder, position: Int) {
        (holder).bind(gifList.get(position))
    }

    override fun getItemCount(): Int = gifList.size

    fun submitList(list: List<Data>)
    {
        gifList = list
        notifyDataSetChanged()
    }
}