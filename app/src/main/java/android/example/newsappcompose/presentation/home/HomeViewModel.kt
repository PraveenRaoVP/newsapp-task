package android.example.newsappcompose.presentation.home

import android.example.newsappcompose.domain.usecases.news.NewsUseCases
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    val news = newsUseCases.getNews(
        listOf("bbc-news","abc-news","al-jazeera-english","google-news")
    ).cachedIn(viewModelScope)
}