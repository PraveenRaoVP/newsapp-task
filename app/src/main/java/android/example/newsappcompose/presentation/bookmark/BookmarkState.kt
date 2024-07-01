package android.example.newsappcompose.presentation.bookmark

import android.example.newsappcompose.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)

