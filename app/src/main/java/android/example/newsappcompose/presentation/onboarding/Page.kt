package android.example.newsappcompose.presentation.onboarding

import android.example.newsappcompose.R
import androidx.annotation.DrawableRes

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pagesList = listOf(
    Page(
        title = "Welcome to Noose - News App",
        description = "Get the latest news from around the world",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Personalized News",
        description = "Get news that matters to you",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Stay Updated",
        description = "Stay updated with the latest news",
        image = R.drawable.onboarding3
    )
)