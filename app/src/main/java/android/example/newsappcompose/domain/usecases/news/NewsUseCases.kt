package android.example.newsappcompose.domain.usecases.news

import android.database.Cursor

data class NewsUseCases(
    val getNews: GetNews,
    val searchNews: SearchNews,
    val upsertNews: UpsertArticle,
    val deleteNews: DeleteArticle,
    val searchArticle: GetArticles,
    val selectArticle: SelectArticle,
    val addCustomArticle: AddCustomArticle,
    val getCustomArticle: GetCustomArticle
)
