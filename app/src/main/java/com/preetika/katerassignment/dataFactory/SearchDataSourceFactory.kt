package com.preetika.katerassignment.dataFactory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.preetika.katerassignment.dataSource.SearchGifDataSource
import com.preetika.katerassignment.model.Data
import com.preetika.katerassignment.network.NetworkService
import io.reactivex.disposables.CompositeDisposable

// data factory class to fetch results for search api

class SearchDataSourceFactory (
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService,
    private val searchText:String
)
    : DataSource.Factory<Int, Data>() {

    val searchDataSourceLiveData = MutableLiveData<SearchGifDataSource>()

    override fun create(): DataSource<Int, Data> {
        val searchDataSoirce = SearchGifDataSource(networkService, compositeDisposable,searchText)
        searchDataSourceLiveData.postValue(searchDataSoirce)
        return searchDataSoirce
    }
}