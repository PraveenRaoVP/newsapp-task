package android.example.newsappcompose.data.remote.dto

import android.example.newsappcompose.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)