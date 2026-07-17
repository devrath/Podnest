package com.istudio.podnest.feature.library.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.istudio.podnest.feature.library.LibraryScreen

const val LIBRARY_ROUTE = "library"

fun NavGraphBuilder.libraryScreen(onPodcastClick: (feedId: Long) -> Unit) {
    composable(LIBRARY_ROUTE) {
        LibraryScreen(onPodcastClick = { podcast -> onPodcastClick(podcast.feedId) })
    }
}
