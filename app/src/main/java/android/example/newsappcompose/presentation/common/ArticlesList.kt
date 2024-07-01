package android.example.newsappcompose.presentation.common

import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.model.Source
import android.example.newsappcompose.presentation.Dimens
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles)

    if(handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.mediumPadding1),
            contentPadding = PaddingValues(all = Dimens.extraSmallPadding2)
        ) {
            items(count = articles.itemCount) { index ->
                articles[index]?.let { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onClick(article) }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.mediumPadding1),
            contentPadding = PaddingValues(all = Dimens.extraSmallPadding2)
        ) {
            items(count = articles.size) { index ->
                val article = articles[index]
                ArticleCard(article = article, onClick = { onClick(article) })
            }
    }
}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>
) : Boolean {
    val loadState = articles.loadState
    val error = when{
        loadState.refresh is LoadState.Error -> {
            loadState.refresh as LoadState.Error
        }
        loadState.append is LoadState.Error -> {
            loadState.append as LoadState.Error
        }
        loadState.prepend is LoadState.Error -> {
            loadState.prepend as LoadState.Error
        }
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }
        error != null -> {
            EmptyScreen(error = error)
            false
        }
        else -> {
            true
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.mediumPadding1)
    ) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = Dimens.mediumPadding1)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesListPreview() {
    val dummyArticles = flowOf(
        PagingData.from(
            listOf(
                Article("1", "Content 1", "Description 1", "Author 1", Source(id="1",name ="BBC News"), "Url 1", "UrlToImage 1", "PublishedAt 1"),
                Article("2", "Content 2", "Description 2", "Author 2", Source(id="1",name ="BBC News"), "Url 2", "UrlToImage 2", "PublishedAt 2"),
                // Add more dummy articles if needed
            )
        )
    ).collectAsLazyPagingItems()
    ArticlesList(
        articles = dummyArticles,
        onClick = { }
    )
}