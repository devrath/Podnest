package com.istudio.podnest.feature.discover.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.istudio.podnest.feature.discover.DiscoverScreen

const val DISCOVER_ROUTE = "discover"

/**
 * Registered directly into the app-level NavHost (see `:app`'s
 * PodnestNavHost) — no dynamic-feature indirection needed since dynamic
 * delivery is out of scope for now.
 */
fun NavGraphBuilder.discoverScreen(onPodcastClick: (feedId: Long) -> Unit) {
    composable(DISCOVER_ROUTE) {
        DiscoverScreen(onPodcastClick = { podcast -> onPodcastClick(podcast.feedId) })
    }
}
