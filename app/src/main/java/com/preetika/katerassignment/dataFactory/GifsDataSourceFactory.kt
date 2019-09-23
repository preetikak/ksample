package com.preetika.katerassignment.dataFactory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.preetika.katerassignment.dataSource.GifDataSource
import com.preetika.katerassignment.model.Data
import com.preetika.katerassignment.network.NetworkService
import io.reactivex.disposables.CompositeDisposable

//data factory to get live results for GIFS

class GifsDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService)
    : DataSource.Factory<Int, Data>() {

    val gifsDataSourceLiveData = MutableLiveData<GifDataSource>()

    override fun create(): DataSource<Int, Data> {
        val newsDataSource = GifDataSource(networkService, compositeDisposable)
        gifsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}