package android.example.newsappcompose.presentation.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val query: String) : SearchEvent()

    data object SearchNews : SearchEvent()

    data object ClearSearchQuery : SearchEvent()
}