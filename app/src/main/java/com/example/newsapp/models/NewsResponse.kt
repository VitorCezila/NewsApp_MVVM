package com.example.newsapp.models

data class NewsResponse(
        var articles: MutableList<Article>,
        var status: String,
        var totalResults: Int
)