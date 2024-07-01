package android.example.newsappcompose.presentation.common

import android.content.res.Configuration
import android.example.newsappcompose.R
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.model.Source
import android.example.newsappcompose.presentation.Dimens
import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import android.example.newsappcompose.util.TimeUtils
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
            .clickable { onClick() }
    ) {
        Log.i("ArticleCard", "ArticleCard: ${article.urlToImage}")
        if(article.urlToImage.contains("content://")) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(Uri.parse(article.urlToImage))
                        .size(70, 70)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimens.articleCardSize)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(end = Dimens.extraSmallPadding)
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .size(Dimens.articleCardSize)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(end = Dimens.extraSmallPadding),
                model = ImageRequest
                    .Builder(context)
                    .data(article.urlToImage)
                    .build(),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.extraSmallPadding)
                .height(Dimens.articleCardSize),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(Dimens.extraSmallPadding2))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.smallIconSize),
                    colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(Dimens.extraSmallPadding2))
                Text(
                    text = "TimeUtils.getTimeAgo(article.publishedAt)",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.body)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ArticleCardPreview() {
    NewsAppComposeTheme {
        ArticleCard(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            article = Article(
                author = "BBC",
                content = "ABCDEFG",
                description = "BBC News",
                publishedAt = "2021-09-01T00:00:00Z",
                source = Source(id = "1", name = "BBC"),
                title = "New news New News News news adwawdw awdaw awdaw awd awdwa daw",
                url = "https://www.bbc.com",
                urlToImage = "https://www.bbc.com",
                isCustomArticle = false
            ),
            onClick = {}
        )
    }
}