package com.preetika.katerassignment.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.preetika.katerassignment.R
import com.preetika.katerassignment.model.Data

class GIfViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: Data?) {
        if (item != null) {
           var imageView: ImageView = (itemView.findViewById<ImageView>(R.id.gif))
           var chkFav: CheckBox = (itemView.findViewById<CheckBox>(R.id.chkFav))
            Glide.with(context).load(item.images.preview_gif.gifUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
            if(isFavFragment)
            {
                chkFav.visibility = View.GONE
            }
            else
                chkFav.isChecked = item.images.preview_gif.isFavorite

        }
    }

    companion object {
        private lateinit var context: Context
        private var isFavFragment : Boolean = false
        fun create(parent: ViewGroup,isFavF:Boolean=false): GIfViewHolder {
            context = parent.context;
            isFavFragment = isFavF
            val view = LayoutInflater.from(context)
                .inflate(R.layout.item_gif, parent, false)
            return GIfViewHolder(view)
        }
    }
}