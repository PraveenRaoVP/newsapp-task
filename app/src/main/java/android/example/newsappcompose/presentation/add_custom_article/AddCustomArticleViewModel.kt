package android.example.newsappcompose.presentation.add_custom_article

import android.content.ClipData
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.model.Source
import android.example.newsappcompose.domain.usecases.news.NewsUseCases
import android.example.newsappcompose.util.TimeUtils
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddCustomArticleViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    val uiState = mutableStateOf(AddCustomArticleState())

    fun onAuthorChange(newValue: String) {
        uiState.value = uiState.value.copy(author = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(description = newValue)
    }

    fun onTitleChange(newValue: String) {
        uiState.value = uiState.value.copy(title = newValue)
    }

    fun onDragDropImage(imageUrl: String) {
        uiState.value = uiState.value.copy(imageUrl = imageUrl)
    }




    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: AddCustomArticleEvent) {
        when(event) {
            is AddCustomArticleEvent.AddCustomArticle -> {
                val article = Article(
                    author = uiState.value.author,
                    title = uiState.value.title,
                    content = uiState.value.description,
                    url = uiState.value.imageUrl!!,
                    urlToImage = uiState.value.imageUrl!!,
                    publishedAt = LocalDate.now().toString(),
                    description = "",
                    source = Source(id=uiState.value.author, name=uiState.value.author),
                    isCustomArticle = true
                )
                viewModelScope.launch {
                    newsUseCases.addCustomArticle(article)
                }
            }
            is AddCustomArticleEvent.AuthorChange -> {
                onAuthorChange(event.author)
            }
            is AddCustomArticleEvent.DescriptionChange -> {
                onDescriptionChange(event.description)
            }
            is AddCustomArticleEvent.TitleChange -> {
                onTitleChange(event.title)
            }

            is AddCustomArticleEvent.DragDropImage -> {
                onDragDropImage(event.imageUrl)
            }
            is AddCustomArticleEvent.UpdateImageByClipData -> {
                updateImageByClipData(event.clipData)
            }
        }
    }

    fun updateImageByClipData(clipData: ClipData?) {
        if (clipData == null) return

        val imageUri = (0 until clipData.itemCount)
            .mapNotNull { index -> clipData.getItemAt(index).uri }
            .firstOrNull()

        imageUri?.let {
            onDragDropImage(it.toString())
        }
    }
}