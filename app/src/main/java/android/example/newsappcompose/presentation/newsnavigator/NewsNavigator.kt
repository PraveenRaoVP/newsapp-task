package android.example.newsappcompose.presentation.newsnavigator

import android.example.newsappcompose.R
import android.example.newsappcompose.domain.model.Article
import android.example.newsappcompose.presentation.add_custom_article.AddCustomArticleScreen
import android.example.newsappcompose.presentation.add_custom_article.AddCustomArticleViewModel
import android.example.newsappcompose.presentation.bookmark.BookmarkScreen
import android.example.newsappcompose.presentation.bookmark.BookmarkViewModel
import android.example.newsappcompose.presentation.details.DetailsEvent
import android.example.newsappcompose.presentation.details.DetailsScreen
import android.example.newsappcompose.presentation.details.DetailsViewModel
import android.example.newsappcompose.presentation.home.HomeScreen
import android.example.newsappcompose.presentation.home.HomeViewModel
import android.example.newsappcompose.presentation.navgraph.Route
import android.example.newsappcompose.presentation.newsnavigator.components.BottomNavigationItem
import android.example.newsappcompose.presentation.newsnavigator.components.NewsBottomNavigation
import android.example.newsappcompose.presentation.search.SearchEvent
import android.example.newsappcompose.presentation.search.SearchScreen
import android.example.newsappcompose.presentation.search.SearchViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun NewsNavigator() {
    val bottomNavItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, label = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, label = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, label = "Bookmarks")
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarkScreen.route -> 2
        else -> 0
    }

    Scaffold(
        bottomBar = {
            if(isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavItems,
                    selectedItem = selectedItem,
                    onItemClicked = { index ->
                        when (index) {
                            0 -> navigateToTab(navController, Route.HomeScreen.route)
                            1 -> navigateToTab(navController, Route.SearchScreen.route)
                            2 -> navigateToTab(navController, Route.BookmarkScreen.route)
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            // a plus icon
            IconButton(onClick = {
                navigateToTab(
                    navController,
                    Route.AddCustomArticleScreen.route
                )
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add custom article")
            }
        }
    ) { innerPadding ->
        val bottomPadding = innerPadding.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()

                HomeScreen(
                    articles = articles,
                    navigateToSearch = { navigateToTab(navController, Route.SearchScreen.route) },
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    }
                )
            }

            composable(Route.AddCustomArticleScreen.route) {
                val viewModel: AddCustomArticleViewModel = hiltViewModel()
                AddCustomArticleScreen(
                    state = viewModel.uiState.value,
                    onEvent = viewModel::onEvent,
                    navigateToPopUp = { to, popUp -> navigateAndPopUp(navController, to, popUp)}
                )
            }

            composable(Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(state = state,
                    onEvent = viewModel::onEvent,
                    navigate = {
                        navigateToDetails(
                            navController = navController,
                            article = it
                        )
                    },
                    onCategoryClicked = {
                        viewModel.onEvent(SearchEvent.UpdateSearchQuery(it))
                        viewModel.onEvent(SearchEvent.SearchNews)
                    }
                )
            }

            composable(Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
                    ?.let { article ->
                        LaunchedEffect(article) {
                            viewModel.onEvent(DetailsEvent.CheckIfBookmarked(article))
                        }
                        if(viewModel.sideEffect != null) {
                            Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                            viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                        }
                        DetailsScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = navController::navigateUp,
                            isBookmarked = viewModel.isBookmarked
                        )
                    }
            }

            composable(Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value

                BookmarkScreen(state = state, navigateTo = { navigateToDetails(navController, it) })
            }
        }
    }

}

fun navigateAndPopUp(navController: NavHostController, to: String, popUp: String) {
    navController.navigate(to) {
        popUpTo(popUp) {
            inclusive = true
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.DetailsScreen.route)
}