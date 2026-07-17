package com.istudio.podnest.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.istudio.podnest.feature.search.SearchScreen

const val SEARCH_ROUTE = "search"

fun NavGraphBuilder.searchScreen(onPodcastClick: (feedId: Long) -> Unit) {
    composable(SEARCH_ROUTE) {
        SearchScreen(onPodcastClick = { podcast -> onPodcastClick(podcast.feedId) })
    }
}
