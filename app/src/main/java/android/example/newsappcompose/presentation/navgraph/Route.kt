package android.example.newsappcompose.presentation.navgraph

sealed class Route(
    val route: String
) {
    data object OnboardingScreen : Route("onboardingScreen")
    data object HomeScreen : Route("homeScreen")
    data object SearchScreen : Route("searchScreen")
    data object BookmarkScreen : Route("bookmarkScreen")
    data object DetailsScreen : Route("detailsScreen")

    data object AppStartNavigation : Route("appStartNavigation")
    data object NewsNavigation : Route("newsNavigation")
    data object NewsNavigatorScreen : Route("newsNavigatorScreen")

    data object AddCustomArticleScreen : Route("addCustomArticleScreen")
}