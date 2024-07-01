package android.example.newsappcompose.domain.usecases.news

import android.example.newsappcompose.domain.repository.NewsRepository

class GetCustomArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke() = newsRepository.getCustomArticles()
}