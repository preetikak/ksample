package com.preetika.katerassignment.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.preetika.katerassignment.model.Data
import com.preetika.katerassignment.network.NetworkService
import com.preetika.katerassignment.network.State
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers



class GifDataSource(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
)
    : PageKeyedDataSource<Int, Data>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Data>) {
        updateState(State.LOADING)
        compositeDisposable.add(
                networkService.getTrendingGifs(1, params.requestedLoadSize)
                        .subscribe(
                                { response ->
                                    updateState(State.DONE)
                                    callback.onResult(response.data,
                                            null,
                                            2
                                    )
                                },
                                {
                                    updateState(State.ERROR)
                                    setRetry(Action { loadInitial(params, callback) })
                                }
                        )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Data>) {
        updateState(State.LOADING)
        compositeDisposable.add(
                networkService.getTrendingGifs(params.key, params.requestedLoadSize)
                        .subscribe(
                                { response ->
                                    updateState(State.DONE)
                                    callback.onResult(response.data,
                                            params.key + 1
                                    )
                                },
                                {
                                    updateState(State.ERROR)
                                    setRetry(Action { loadAfter(params, callback) })
                                }
                        )
        )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Data>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}