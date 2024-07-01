package android.example.newsappcompose.presentation.bookmark

import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.usecases.news.NewsUseCases
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            try {
                val articles: Flow<List<Article>> = newsUseCases.getCustomArticle()
                articles.onEach { articleList ->
                    _state.value = _state.value.copy(articles = articleList)
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                Log.e("BookmarkViewModel", "Error fetching articles", e)
                // Handle error scenario, such as showing a snackbar or retry mechanism
            }
        }
    }
}