package android.example.newsappcompose.data.remote

import android.example.newsappcompose.domain.model.Article
import androidx.paging.PagingSource
import androidx.paging.PagingState

class SearchNewsPagingSource(
    private val newsApi: NewsApi,
    private val searchQuery: String,
    private val sources: String
) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.prevKey?.minus(1)
        } // Return the previous key if it's not null, otherwise return the next key. what is key here? It's the page number.
    }

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.searchNews(
                searchQuery,
                page,
                sources
            )
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1
            ) // If the total number of news is equal to the total number of results, return null for the next key, otherwise return the next page number.
            // what foes LoadResult.Page do? It returns a page of data with the previous key and the next key.
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}