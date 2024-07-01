package android.example.newsappcompose.presentation.search

import android.example.newsappcompose.domain.model.Article
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class SearchState (
    val searchQuery: String = "",
    val results: Flow<PagingData<Article>>? = null
)