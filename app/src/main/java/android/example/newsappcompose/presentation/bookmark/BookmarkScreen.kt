package android.example.newsappcompose.presentation.bookmark

import android.example.newsappcompose.R
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.presentation.Dimens
import android.example.newsappcompose.presentation.common.ArticlesList
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateTo: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                top = Dimens.mediumPadding1,
                start = Dimens.mediumPadding1,
                end = Dimens.mediumPadding1
            )
    ) {
        Text(
            text = "Bookmarks",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_title)
        )

        Spacer(modifier = Modifier.height(Dimens.mediumPadding1))

        ArticlesList(articles = state.articles, onClick = { navigateTo(it) })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookmarkScreen() {
    NewsAppComposeTheme {
        BookmarkScreen(BookmarkState()) {}
    }
}