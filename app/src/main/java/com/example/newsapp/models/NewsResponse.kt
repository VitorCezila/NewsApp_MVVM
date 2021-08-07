package com.example.newsapp.models

data class NewsResponse(
        var articles: List<Article>,
        var status: String,
        var totalResults: Int
)