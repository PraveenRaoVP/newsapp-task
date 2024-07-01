package android.example.newsappcompose.presentation.search

import android.example.newsappcompose.domain.usecases.news.NewsUseCases
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state


    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = event.query)
            }
            is SearchEvent.SearchNews -> {
                searchNews()
            }

            is SearchEvent.ClearSearchQuery -> {
                _state.value = _state.value.copy(searchQuery = "")
            }
        }
    }

    private fun searchNews() {
        val articles = newsUseCases.searchNews(state.value.searchQuery, listOf("bbc-news","abc-news","al-jazeera-english","google-news"))
            .cachedIn(viewModelScope)
        _state.value = _state.value.copy(results = articles)
    }
}