package com.example.newsaggregatorapp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.currentsapi.services/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}