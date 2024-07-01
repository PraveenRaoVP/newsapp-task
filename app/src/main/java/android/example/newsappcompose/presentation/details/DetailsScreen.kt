package android.example.newsappcompose.presentation.details

import android.content.Context
import android.content.Intent
import android.example.newsappcompose.R
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.domain.model.Source
import android.example.newsappcompose.presentation.Dimens
import android.example.newsappcompose.presentation.details.components.DetailsTopBar
import android.example.newsappcompose.presentation.newsnavigator.navigateAndPopUp
import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
    isBookmarked: Boolean
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                browsingClicked(article, context)
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = { event(DetailsEvent.UpsertDeleteArticle(article)) },
            onBackClick = navigateUp,
            isBookmarked = isBookmarked,
            isCustomArticle = article.isCustomArticle,
            onDeleteClick = {
                event(DetailsEvent.DeleteCustomArticle(article))
                navigateUp()
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = Dimens.mediumPadding1,
                end = Dimens.mediumPadding1,
                top = Dimens.mediumPadding1
            )
        ) {
            item {
                if(article.urlToImage.contains("content://")) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(Uri.parse(article.urlToImage))
                                .size(200,200)
                                .build()
                        ),
                        contentDescription = null
                    )
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(context = context).data(article.urlToImage)
                            .build(),
                        contentDescription = null,
                        ImageLoader(context),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(248.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.mediumPadding1))

                Text(
                    text = article.title, style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )

                Text(
                    text = article.content, style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )

                if(!article.isCustomArticle) {
                    Button(onClick = {
                        browsingClicked(article, context)
                    },
                        modifier = Modifier.background(Color.Transparent),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "view more...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Blue,
                            modifier = Modifier.align(Alignment.Bottom)
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    NewsAppComposeTheme {
        DetailsScreen(
            article = Article(
                "ABC",
                "ABCD",
                "ABC",
                "2020",
                Source("1", "ABC"),
                "ABC",
                "https://consent.yahoo.com/v2/collectConsent?sessionId=1_cc-session_6a6ab0dd-00f8-4399-ad8d-1d7bc76dd1bb",
                "https://cdn.vox-cdn.com/thumbor/xR_04L4hMaOHPWLNRC2H-WiJSmU=/0x0:2040x1360/1200x628/filters:focal(1020x680:1021x681)/cdn.vox-cdn.com/uploads/chorus_asset/file/24492430/STK459_Music_Headphones.jpg",
                false
            ),
            event = {},
            navigateUp = {},
            isBookmarked = true,
        )
    }
}

fun browsingClicked(article: Article, context: Context) {
    Intent(Intent.ACTION_VIEW).also {
        it.data = Uri.parse(article.url)
        if (it.resolveActivity(context.packageManager) != null) {
            context.startActivity(it)
        }
    }
}