package android.example.newsappcompose.presentation.details.components

import android.content.res.Configuration
import android.example.newsappcompose.R
import android.example.newsappcompose.domain.usecases.news.AddCustomArticle
import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBrowsingClick : () -> Unit = {},
    onShareClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit = {},
    isBookmarked: Boolean,
    isCustomArticle: Boolean
) {
    TopAppBar(title = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
            actionIconContentColor = colorResource(id = R.color.body),
            navigationIconContentColor = colorResource(id = R.color.body),
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(painter = painterResource(id = R.drawable.ic_back_arrow), contentDescription = null)
            }
        },
        actions = {
            if(!isCustomArticle) {
                IconButton(onClick = onBrowsingClick) {
                    Icon(painter = painterResource(id = R.drawable.ic_network), contentDescription = null)
                }
            } else {
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                }
            }
            IconButton(onClick = onShareClick) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
            IconButton(onClick = onBookmarkClick) {
                if(!isBookmarked) {
                    Icon(painter = painterResource(id = R.drawable.ic_bookmark), contentDescription = null)
                } else {
                    Icon(painter = painterResource(id = R.drawable.ic_bookmark_filled), contentDescription = null)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsTopBarPreview() {
    NewsAppComposeTheme {
        DetailsTopBar(
            onBrowsingClick = {},
            onShareClick = {},
            onBookmarkClick = {},
            onBackClick = {},
            isBookmarked = true,
            isCustomArticle = true
        )
    }
}