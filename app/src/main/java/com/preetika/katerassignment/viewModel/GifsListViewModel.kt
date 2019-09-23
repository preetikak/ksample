package com.preetika.katerassignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.preetika.katerassignment.model.Data
import com.preetika.katerassignment.dataSource.GifDataSource
import com.preetika.katerassignment.dataFactory.GifsDataSourceFactory
import com.preetika.katerassignment.dataFactory.SearchDataSourceFactory
import com.preetika.katerassignment.dataSource.SearchGifDataSource
import com.preetika.katerassignment.network.NetworkService
import com.preetika.katerassignment.network.State
import io.reactivex.disposables.CompositeDisposable

class GifsListViewModel : ViewModel() {

    private val networkService = NetworkService.getService()
    var gifsList: LiveData<PagedList<Data>>
    lateinit var searchList: LiveData<PagedList<Data>>
    var favoriteGifsList: MutableLiveData<List<Data>>
    private val compositeDisposable = CompositeDisposable()
    private val searchCompleteDisposable = CompositeDisposable()
    private val pageSize = 1
    private var gifsDataSourceFactory: GifsDataSourceFactory
    private lateinit var searchDataSourceFactory: SearchDataSourceFactory
    val config = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setInitialLoadSizeHint(pageSize * 2)
        .setEnablePlaceholders(false)
        .build()

    init {
        gifsDataSourceFactory =
            GifsDataSourceFactory(compositeDisposable, networkService)
          gifsList = LivePagedListBuilder<Int, Data>(gifsDataSourceFactory, config).build()
        favoriteGifsList = MutableLiveData<List<Data>>()
    }
    fun search(text:String){
        searchDataSourceFactory =
            SearchDataSourceFactory(searchCompleteDisposable, networkService,text)
        searchList = LivePagedListBuilder<Int, Data>(searchDataSourceFactory, config).build()
    }
    fun setFavoriteList(list :List<Data>)
    {
        favoriteGifsList.value = list
    }

    fun getState(): LiveData<State> = Transformations.switchMap<GifDataSource,
            State>(gifsDataSourceFactory.gifsDataSourceLiveData, GifDataSource::state)


    fun retry() {
        gifsDataSourceFactory.gifsDataSourceLiveData.value?.retry()
    }


    fun listIsEmpty(): Boolean {
        return gifsList.value?.isEmpty() ?: true
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
        searchCompleteDisposable.dispose()
    }


}
