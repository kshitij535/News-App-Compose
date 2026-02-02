package com.example.newsaggregatorapp

data class NewsResponse(
    val news: List<Article>,
    val status: String
)

data class Article(
    val title: String?,
    val description: String?,
    val image: String?,
    val url: String?
)