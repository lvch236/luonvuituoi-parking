package com.example.luonvuituoi.services

import com.example.luonvuituoi.item.HotNews
import retrofit2.http.GET
import retrofit2.http.Query

interface HotNewApis {

    @GET("everything")
    suspend fun listNowPlayMovies(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("language") language: String,
    ): HotNews
}