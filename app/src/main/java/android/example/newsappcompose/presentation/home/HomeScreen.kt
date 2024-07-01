package android.example.newsappcompose.presentation.home

import android.content.res.Configuration
import android.example.newsappcompose.R
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.model.Source
import android.example.newsappcompose.presentation.Dimens
import android.example.newsappcompose.presentation.common.ArticlesList
import android.example.newsappcompose.presentation.common.SearchBar
import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToDetails: (Article) -> Unit,
    navigateToSearch: () -> Unit
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(0, 9))
                    .joinToString(separator = " \uD83d\uDFE5 ") { it.title } // 🏥
            } else {
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(Dimens.extraSmallPadding2)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            )
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                color = colorResource(id = R.color.text_title),
            )
        }

        Spacer(modifier = Modifier.height(Dimens.mediumPadding1))

        SearchBar(
            text = "",
            onSearch = { },
            onValueChange = {},
            readOnly = true,
            onClick = { navigateToSearch() },
            onClear = {})

        if(isPortrait()) {
            Spacer(modifier = Modifier.height(Dimens.mediumPadding1))

            Text(
                text = titles,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimens.mediumPadding1, end = Dimens.mediumPadding1)
                    .basicMarquee(),
                fontSize = 13.sp,
                color = colorResource(id = R.color.placeholder)
            )
        }

        ArticlesList(
            modifier = Modifier.padding(Dimens.extraSmallPadding),
            articles = articles,
            onClick = { navigateToDetails(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyArticles = flowOf(
        PagingData.from(
            listOf(
                Article(
                    "1",
                    "Content 1",
                    "Description 1",
                    "Author 1",
                    Source(id = "1", name = "BBC News"),
                    "Url 1",
                    "UrlToImage 1",
                    "PublishedAt 1"
                ),
                Article(
                    "2",
                    "Content 2",
                    "Description 2",
                    "Author 2",
                    Source(id = "1", name = "BBC News"),
                    "Url 2",
                    "UrlToImage 2",
                    "PublishedAt 2"
                ),
                // Add more dummy articles if needed
            )
        )
    ).collectAsLazyPagingItems()
    NewsAppComposeTheme {
        HomeScreen(
            articles = dummyArticles,
            navigateToSearch = {},
            navigateToDetails = {}
        )
    }
}

@Composable
fun isPortrait() : Boolean {
    val config = LocalConfiguration.current
    return config.orientation == Configuration.ORIENTATION_PORTRAIT
}