package android.example.newsappcompose.domain.usecases.news

import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.repository.NewsRepository
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources)
    }
}