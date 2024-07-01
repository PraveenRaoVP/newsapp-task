package android.example.newsappcompose.presentation.search.components

import android.example.newsappcompose.ui.theme.NewsAppComposeTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CategoryVerticalGrid(
    onClick: (String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(10.dp)
        ) {
        items(categoryList.size) { index ->
            CategoryChip(
                category = categoryList[index].categoryName,
                imageUrl = categoryList[index].imageUrl,
                onClick = { onClick(categoryList[index].categoryName) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryVerticalGridPreview() {
    NewsAppComposeTheme {
        CategoryVerticalGrid(onClick = {})
    }
}

data class CategoryDetails(
    val categoryName: String,
    val imageUrl: String
)

val categoryList = listOf(
    CategoryDetails(
        categoryName = "Sports",
        imageUrl = "https://www.mykhel.com/img/2019/12/yuvraj-singh-ipl-1576072430.jpg"
    ),
    CategoryDetails(
        categoryName = "Business",
        imageUrl = "https://st2.depositphotos.com/3591429/5246/i/450/depositphotos_52467319-stock-photo-business-people-working-in-office.jpg"
    ),
    CategoryDetails(
        categoryName = "National",
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0jpkq5EbhL-lT7yW4m27_pos1_cQ0kK7aCw&s"
    ),
    CategoryDetails(
        categoryName = "World",
        imageUrl = "https://img.freepik.com/free-photo/3d-rendering-planet-earth_23-2150498436.jpg?size=338&ext=jpg&ga=GA1.1.2082370165.1717113600&semt=sph",
    ),
    CategoryDetails(
        categoryName = "Technology",
        imageUrl = "https://cdn.pixabay.com/photo/2015/07/28/22/01/office-865091_640.jpg"
    ),
    CategoryDetails(
        categoryName = "Entertainment",
        imageUrl = "https://images.pexels.com/photos/1763075/pexels-photo-1763075.jpeg?cs=srgb&dl=pexels-sebastian-ervi-866902-1763075.jpg&fm=jpg"
    ),
    CategoryDetails(
        categoryName = "Politics",
        imageUrl = "https://samriddhi.org/wp-content/uploads/2017/08/Politics.jpg"
    ),
    CategoryDetails(
        categoryName = "Science",
        imageUrl = "https://media.istockphoto.com/id/1019131612/vector/science-template-wallpaper-or-banner-with-a-dna-molecules.jpg?s=612x612&w=0&k=20&c=MAUgjDBjVplQ6N-PoYtllThhOVt3_db3r8LtVe3hUMU="
    )
)