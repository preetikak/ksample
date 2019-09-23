package com.preetika.katerassignment.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.preetika.katerassignment.model.Data
import com.preetika.katerassignment.network.State
import com.preetika.katerassignment.viewModel.GifsListViewModel
import kotlinx.android.synthetic.main.item_gif.view.*

class GifsListAdapter(val viewModel: GifsListViewModel)
    : PagedListAdapter<Data, RecyclerView.ViewHolder>(GifsDiffCallback) {


    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GIfViewHolder.create(parent)
    }

    override fun onBindViewHolder( holder: RecyclerView.ViewHolder, position: Int) {

        val gifHolder = holder as GIfViewHolder
        gifHolder.bind(getItem(position))
        gifHolder.itemView.chkFav.setOnClickListener{
            val item = getItem(position)
            if(item!!.images.preview_gif.isFavorite)
                item.images.preview_gif.isFavorite = false
            else
                item.images.preview_gif.isFavorite = true
            submitFavList()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemCount()
    }

    companion object {
        val GifsDiffCallback = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.images.preview_gif.gifUrl == newItem.images.preview_gif.gifUrl
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    fun submitFavList(){

        val iterator = currentList!!.iterator()
        var favList = ArrayList<Data>()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if(item.images.preview_gif.isFavorite)
            favList.add(item)
        }
        viewModel.setFavoriteList(favList)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}