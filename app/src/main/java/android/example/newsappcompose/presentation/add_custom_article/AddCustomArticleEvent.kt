package android.example.newsappcompose.presentation.add_custom_article

import android.content.ClipData
import android.example.newsappcompose.domain.model.Article

sealed interface AddCustomArticleEvent {
    data object AddCustomArticle: AddCustomArticleEvent
    data class TitleChange(val title: String) : AddCustomArticleEvent
    data class AuthorChange(val author: String) : AddCustomArticleEvent
    data class DescriptionChange(val description: String) : AddCustomArticleEvent
    data class DragDropImage(val imageUrl: String) : AddCustomArticleEvent
    data class UpdateImageByClipData(val clipData: ClipData) : AddCustomArticleEvent
}