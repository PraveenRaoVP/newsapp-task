package android.example.newsappcompose.data.repository

import android.example.newsappcompose.data.local.NewsDao
import android.example.newsappcompose.data.remote.NewsApi
import android.example.newsappcompose.data.remote.NewsPagingSources
import android.example.newsappcompose.data.remote.SearchNewsPagingSource
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.repository.NewsRepository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> { // gets news from the source page by page. This requires the api to be paginated.
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                NewsPagingSources(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun getSearchedNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    newsApi = newsApi,
                    searchQuery = searchQuery,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override suspend fun upsertNews(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsDao.delete(article)
    }

    override suspend fun selectArticle(url: String): Article? {
        return newsDao.getArticle(url)
    }

    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles().onEach { it.reversed() }
    }

    override suspend fun addCustomArticle(article: Article) {
        newsDao.addCustomArticle(article)
    }

    override fun getCustomArticles(): Flow<List<Article>> {
        return newsDao.getCustomArticles()
    }
}