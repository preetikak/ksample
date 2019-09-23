package com.preetika.katerassignment.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.preetika.katerassignment.R
import com.preetika.katerassignment.network.State
import com.preetika.katerassignment.ui.adapter.GifsListAdapter
import com.preetika.katerassignment.viewModel.GifsListViewModel
import com.giphysample.app.viewModel.PageViewModel
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.fragment_trending.view.*

class TrendingGifs : Fragment() {
    private lateinit var pageViewModel: PageViewModel
    private lateinit var viewModel: GifsListViewModel
    private lateinit var listAdapter: GifsListAdapter
    private lateinit var rootView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getString(TrendingGifs.ARG_SECTION_NAME) ?: "")
        }
        viewModel = ViewModelProviders.of(activity!!).get(GifsListViewModel::class.java)
    }
    private fun initAdapter() {
        listAdapter = GifsListAdapter(viewModel)
        rootView.rvGifs.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rootView.rvGifs.adapter = listAdapter
        viewModel.gifsList.observe(this, Observer {
            listAdapter.submitList(it)
        })

    }
    private fun initSearch(txt:String)
    {
        if(txt=="")
            viewModel.gifsList.observe(this, Observer {
                listAdapter.submitList(it)
            })
        else
        viewModel.searchList.observe(this, Observer {
            listAdapter.submitList(it)
        })
    }
    private fun initState() {
        rootView.txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            rootView.progress_bar.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            rootView.txt_error.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                listAdapter.setState(state ?: State.DONE)
            }
        })
        rootView.etSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                    viewModel.search(p0.toString())
                initSearch(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_trending, container, false)
        initAdapter()
        initState()
        return rootView
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
        fun newInstance(context : Context, sectionName: String): TrendingGifs {
            return TrendingGifs().apply {
                arguments = Bundle().apply {
                    putString(ARG_SECTION_NAME, sectionName)
                }
            }
        }
    }
}