package com.istudio.podnest.feature.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.istudio.podnest.core.model.Episode
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.core.ui.components.EmptyView
import com.istudio.podnest.core.ui.components.EpisodeRow
import com.istudio.podnest.core.ui.components.ErrorView
import com.istudio.podnest.core.ui.components.LoadingView
import com.istudio.podnest.core.ui.components.PodcastCard
import com.istudio.podnest.core.ui.components.PodnestTopBar
import com.istudio.podnest.core.ui.components.SectionHeader

@Composable
fun DiscoverScreen(
    onPodcastClick: (Podcast) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading && uiState.trendingPodcasts.isEmpty() -> LoadingView(modifier)
        uiState.errorMessage != null && uiState.trendingPodcasts.isEmpty() ->
            ErrorView(message = uiState.errorMessage.orEmpty(), onRetry = {}, modifier = modifier)

        else -> DiscoverContent(
            uiState = uiState,
            onPodcastClick = onPodcastClick,
            onEpisodeClick = viewModel::onEpisodeClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun DiscoverContent(
    uiState: DiscoverUiState,
    onPodcastClick: (Podcast) -> Unit,
    onEpisodeClick: (Episode) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
    ) {
        item { PodnestTopBar(title = "Podnest") }

        if (uiState.trendingPodcasts.isNotEmpty()) {
            item { SectionHeader(title = "Trending Now") }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    items(uiState.trendingPodcasts, key = { it.feedId }) { podcast ->
                        PodcastCard(podcast = podcast, onClick = onPodcastClick)
                    }
                }
            }
        }

        if (uiState.recentEpisodes.isNotEmpty()) {
            item { SectionHeader(title = "Fresh Episodes") }
            items(uiState.recentEpisodes, key = { it.id }) { episode ->
                EpisodeRow(episode = episode, onClick = onEpisodeClick)
            }
        }

        if (uiState.trendingPodcasts.isEmpty() && uiState.recentEpisodes.isEmpty()) {
            item { EmptyView(message = "Nothing to discover yet — check your connection.") }
        }
    }
}
