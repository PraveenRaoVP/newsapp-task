package android.example.newsappcompose.domain.usecases.news

import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.repository.NewsRepository
import android.example.newsappcompose.presentation.search.SearchEvent
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getSearchedNews(searchQuery, sources)
    }
}