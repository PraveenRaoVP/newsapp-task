package android.example.newsappcompose.presentation.search.components

import android.example.newsappcompose.R
import android.example.newsappcompose.presentation.Dimens
import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun CategoryChip(
    category: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(10.dp)
            .width(100.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest
                .Builder(context = context)
                .data(imageUrl)
                .error(R.drawable.ic_network_error)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = category,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = Dimens.extraSmallPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryChipPreview() {
    NewsAppComposeTheme {
        CategoryChip(
            category = "Technology",
            imageUrl = "https://cdn.vox-cdn.com/thumbor/o2eZ-UI17WzzB0FGKz23I7FMzUE=/0x0:2040x1360/1200x628/filters:focal(1020x680:1021x681)/cdn.vox-cdn.com/uploads/chorus_asset/file/24128002/226361_Apple_iPad_10.9_10th_gen_DSeifert_0001.jpg",
            onClick = {}
        )
    }
}
