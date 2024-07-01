package android.example.newsappcompose.presentation.add_custom_article

data class AddCustomArticleState(
    val title: String = "",
    val imageUrl: String? = null,
    val description: String = "",
    val author: String = ""
)
