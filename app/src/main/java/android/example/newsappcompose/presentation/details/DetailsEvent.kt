package android.example.newsappcompose.presentation.details

import android.example.newsappcompose.domain.model.Article

sealed class DetailsEvent {
//    data object SaveArticle : DetailsEvent()

    data class UpsertDeleteArticle(val article: Article) : DetailsEvent()

    data object RemoveSideEffect : DetailsEvent()

    data class CheckIfBookmarked(val article: Article) : DetailsEvent()

    data class DeleteCustomArticle(val article: Article) : DetailsEvent()
}