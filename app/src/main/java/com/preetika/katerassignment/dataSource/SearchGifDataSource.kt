package com.preetika.katerassignment.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.preetika.katerassignment.model.Data
import com.preetika.katerassignment.network.NetworkService
import com.preetika.katerassignment.network.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchGifDataSource(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable,
    private val searchText: String
) : PageKeyedDataSource<Int, Data>() {

    var state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, Data>
    ) {
            updateState(State.LOADING)
            compositeDisposable.add(
                    networkService.searchGifs(searchText, 1, params.requestedLoadSize)
                        .subscribe(
                            { response ->
                                updateState(State.DONE)
                                callback.onResult(
                                    response.data,
                                    null,
                                    2
                                )
                            },
                            {
                                updateState(State.ERROR)
                            }
                        )
            )
    }

    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, Data>
    ) {
            updateState(State.LOADING)
            compositeDisposable.add(
                    networkService.searchGifs(searchText, params.key, params.requestedLoadSize)
                        .subscribe(
                            { response ->
                                updateState(State.DONE)
                                callback.onResult(
                                    response.data,
                                    params.key + 1
                                )
                            },
                            {
                                updateState(State.ERROR)
                            }
                        )
            )
    }

    override fun loadBefore(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, Data>
    ) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

}