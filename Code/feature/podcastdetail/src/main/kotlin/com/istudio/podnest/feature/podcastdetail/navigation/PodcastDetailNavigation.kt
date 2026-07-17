package com.istudio.podnest.feature.podcastdetail.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.istudio.podnest.feature.podcastdetail.PodcastDetailScreen

private const val FEED_ID_ARG = "feedId"
const val PODCAST_DETAIL_ROUTE = "podcastDetail/{$FEED_ID_ARG}"

fun podcastDetailRoute(feedId: Long): String = "podcastDetail/$feedId"

/** Type-safe wrapper around [SavedStateHandle] for this screen's single nav argument. */
class PodcastDetailArgs(savedStateHandle: SavedStateHandle) {
    val feedId: Long = checkNotNull(savedStateHandle[FEED_ID_ARG]) { "feedId argument is required" }
}

fun NavGraphBuilder.podcastDetailScreen(onBackClick: () -> Unit) {
    composable(
        route = PODCAST_DETAIL_ROUTE,
        arguments = listOf(navArgument(FEED_ID_ARG) { type = NavType.LongType }),
    ) {
        PodcastDetailScreen(onBackClick = onBackClick)
    }
}
