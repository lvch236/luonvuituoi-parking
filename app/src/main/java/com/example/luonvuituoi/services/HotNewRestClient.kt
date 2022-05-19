package com.example.luonvuituoi.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HotNewRestClient {
    private var _api : HotNewApis

    val api : HotNewApis
        get() = _api

    init {
        _api = createMovieDBService()
    }

    companion object {
        private var mInstance: HotNewRestClient? = null

        fun getInstance() = mInstance ?: synchronized(this) {
            mInstance ?: HotNewRestClient().also { mInstance = it}
        }
    }

    private fun createMovieDBService(): HotNewApis {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        return retrofit.create(HotNewApis::class.java)
    }
}