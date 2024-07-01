package android.example.newsappcompose.presentation.search

import android.content.res.Configuration
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.presentation.Dimens
import android.example.newsappcompose.presentation.common.ArticlesList
import android.example.newsappcompose.presentation.common.SearchBar
import android.example.newsappcompose.presentation.search.components.CategoryVerticalGrid
import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    navigate: (Article) -> Unit,
    onCategoryClicked: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = Dimens.mediumPadding1,
                start = Dimens.mediumPadding1,
                end = Dimens.mediumPadding1
            )
            .statusBarsPadding()
    ) {
        SearchBar(
            text = state.searchQuery,
            onValueChange = { onEvent(SearchEvent.UpdateSearchQuery(it))},
            onSearch = { onEvent(SearchEvent.SearchNews) },
            readOnly = false,
            onClear = { onEvent(SearchEvent.ClearSearchQuery) }
        )

        Spacer(modifier = Modifier.height(Dimens.mediumPadding1))

        if(state.searchQuery == "") {
            Spacer(modifier = Modifier.height(Dimens.mediumPadding1))
            Text("Categories", style = MaterialTheme.typography.bodyMedium)
             CategoryVerticalGrid(onClick = {
                 onCategoryClicked(it)
             })
        } else {
            state.results?.let {
                val articles = it.collectAsLazyPagingItems()
                ArticlesList(articles = articles, onClick = {
                    navigate(it) })
            }
        }

    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SearchScreenPreview() {
    NewsAppComposeTheme {
        SearchScreen(
            state = SearchState(),
            onEvent = {},
            navigate = {},
            onCategoryClicked = {}
        )
    }
}