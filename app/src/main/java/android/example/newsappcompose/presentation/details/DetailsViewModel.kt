package android.example.newsappcompose.presentation.details

import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.usecases.news.NewsUseCases
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel(){

    var sideEffect by mutableStateOf<String?>(null)
        private set

    var isBookmarked by mutableStateOf(false)
        private set

    fun onEvent(event: DetailsEvent) {
        when(event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(event.article.url)
                    if(article == null) {
                        upsertArticle(event.article)
                    } else {
                        deleteArticle(event.article)
                    }
                    isBookmarked = article == null
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }

            is DetailsEvent.CheckIfBookmarked -> {
                viewModelScope.launch {
                    val article = newsUseCases.selectArticle(event.article.url)
                    isBookmarked = article != null
                }
            }

            is DetailsEvent.DeleteCustomArticle -> {
                viewModelScope.launch {
                    newsUseCases.deleteNews(event.article)
                    sideEffect = "Article Deleted"
                }
            }
        }
    }

    suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertNews(article)
        sideEffect = "Article Inserted"
    }

    suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteNews(article)
        sideEffect = "Article Deleted"
    }
}