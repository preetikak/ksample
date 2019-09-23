package com.preetika.katerassignment.network

import com.preetika.katerassignment.BuildConfig
import com.preetika.katerassignment.model.Response
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


interface NetworkService {

    @GET("/v1/gifs/trending?api_key=gBKHk9txgv7SeHgDu2Gw3VvVY7z4Ff30")
    fun getTrendingGifs(@Query("offset") page: Int, @Query("limit") limit: Int): Single<Response>

    @GET("/v1/gifs/search?api_key=gBKHk9txgv7SeHgDu2Gw3VvVY7z4Ff30")
    fun searchGifs(@Query("q") name: String,@Query("offset") page: Int, @Query("limit") limit: Int): Single<Response>

    companion object {
        private  val timeOut :Long = 60
        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.giphy.com/")

                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient().build())
                    .build()
            return retrofit.create(NetworkService::class.java)
        }
        private fun httpClient(): OkHttpClient.Builder {
            val logging = HttpLoggingInterceptor()
            // set your desired log level
            //logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(timeOut, TimeUnit.SECONDS)
            httpClient.connectTimeout(timeOut, TimeUnit.SECONDS)
            httpClient.cache(null)
            // add your other interceptors …
            // add logging as last interceptor
            httpClient.addInterceptor(logging)

            return httpClient
        }
    }



}