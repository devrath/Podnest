package com.istudio.podnest.feature.library

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.core.ui.components.EmptyView
import com.istudio.podnest.core.ui.components.PodcastCard
import com.istudio.podnest.core.ui.components.PodnestTopBar

@Composable
fun LibraryScreen(
    onPodcastClick: (Podcast) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            PodnestTopBar(title = "Library")
        }

        if (uiState.isEmpty) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                EmptyView(message = "Subscribe to podcasts from their detail page to see them here.")
            }
        } else {
            items(uiState.subscriptions, key = { it.feedId }) { podcast ->
                PodcastCard(
                    podcast = podcast,
                    onClick = onPodcastClick,
                    modifier = Modifier.padding(bottom = 16.dp),
                    size = 100.dp,
                )
            }
        }
    }
}
