package android.example.newsappcompose.domain.usecases.news

import android.example.newsappcompose.data.local.NewsDao
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.repository.NewsRepository

class UpsertArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        newsRepository.upsertNews(article)
    }
}