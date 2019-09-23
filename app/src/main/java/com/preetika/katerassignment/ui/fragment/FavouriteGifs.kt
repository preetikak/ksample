package com.preetika.katerassignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.preetika.katerassignment.R
import com.preetika.katerassignment.ui.adapter.FavoriteListAdapter
import com.preetika.katerassignment.ui.adapter.GifsListAdapter
import com.preetika.katerassignment.viewModel.GifsListViewModel
import kotlinx.android.synthetic.main.fragment_favourite.view.*

/**
 * fragment to show all selected gifs
 */
class FavouriteGifs : Fragment() {
    private lateinit var viewModel: GifsListViewModel
    private lateinit var rootView: View
    private lateinit var listAdapter:FavoriteListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(GifsListViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_favourite, container, false)
        initAdapter()
        return rootView

    }

    // function to assign and initiate adapter
    private fun initAdapter() {
        listAdapter = FavoriteListAdapter ()
        rootView.rvFavGifs.layoutManager = GridLayoutManager(context,2)
        rootView.rvFavGifs.adapter = listAdapter
        viewModel.favoriteGifsList.observe(this, Observer {
                listAdapter.submitList(it)
        })
    }
    companion object {
        /**
         * The fragment argument representing the section name for this
         * fragment.
         */
        private const val ARG_SECTION_NAME = "section_name"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionName: String): FavouriteGifs {
            return FavouriteGifs().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_NAME, sectionName)
                }
            }
        }
    }
}