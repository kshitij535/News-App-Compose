package com.example.newsaggregatorapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v1/latest-news")
    fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String? = null
    ): Call<NewsResponse>
}