package android.example.newsappcompose.domain.usecases.news

import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.repository.NewsRepository

class AddCustomArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        newsRepository.addCustomArticle(article)
    }
}