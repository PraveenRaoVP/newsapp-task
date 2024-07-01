package android.example.newsappcompose.domain.repository

import android.example.newsappcompose.domain.model.Article
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>): Flow<PagingData<Article>>

    fun getSearchedNews(searchQuery: String, sources: List<String>) : Flow<PagingData<Article>>

    suspend fun upsertNews(article: Article)

    suspend fun deleteNews(article: Article)

    suspend fun selectArticle(url: String): Article?

    fun getArticles(): Flow<List<Article>>

    suspend fun addCustomArticle(article: Article)

    fun getCustomArticles(): Flow<List<Article>>
}

/*
why domain layer has only interfaces which are implemented in data layer?
The domain layer contains the business logic of the application. It should not contain any implementation details.
The implementation details should be in the data layer. The domain layer should only contain interfaces and data classes.


Layers of Clean Architecture:-
1. Presentation Layer
2. Domain Layer
3. Data Layer

Presentation layer is the outermost layer and it contains the UI of the application.
Domain layer is the middle layer and it contains the business logic of the application.
Data layer is the innermost layer and it contains the data of the application.


why should we use interfaces?
Interfaces are used to define the contract of a class. It defines
what the class can do but not how it does it. It is used to define the
methods that a class should implement. It is used to achieve abstraction
and polymorphism. It is used to define the behavior of a class.
 */
