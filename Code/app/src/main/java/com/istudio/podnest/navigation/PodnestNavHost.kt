package com.istudio.podnest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.istudio.podnest.feature.discover.navigation.DISCOVER_ROUTE
import com.istudio.podnest.feature.discover.navigation.discoverScreen
import com.istudio.podnest.feature.library.navigation.libraryScreen
import com.istudio.podnest.feature.podcastdetail.navigation.podcastDetailRoute
import com.istudio.podnest.feature.podcastdetail.navigation.podcastDetailScreen
import com.istudio.podnest.feature.search.navigation.searchScreen

/**
 * Assembles every feature module's nav graph into one app-level NavHost.
 *
 * Because dynamic delivery is out of scope, `:app` is allowed to depend on
 * every feature module directly and wire their graphs together here at
 * compile time — no runtime graph registration/ServiceLoader indirection
 * needed (that complexity only pays off once modules become installable
 * on-demand).
 */
@Composable
fun PodnestNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = DISCOVER_ROUTE,
    ) {
        discoverScreen(
            onPodcastClick = { feedId -> navController.navigate(podcastDetailRoute(feedId)) },
        )
        searchScreen(
            onPodcastClick = { feedId -> navController.navigate(podcastDetailRoute(feedId)) },
        )
        libraryScreen(
            onPodcastClick = { feedId -> navController.navigate(podcastDetailRoute(feedId)) },
        )
        podcastDetailScreen(
            onBackClick = { navController.popBackStack() },
        )
    }
}
