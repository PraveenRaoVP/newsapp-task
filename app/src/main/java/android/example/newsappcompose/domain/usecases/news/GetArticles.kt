package android.example.newsappcompose.domain.usecases.news

import android.example.newsappcompose.data.local.NewsDao
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetArticles(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() : Flow<List<Article>> {
        return newsRepository.getArticles()
    }
}