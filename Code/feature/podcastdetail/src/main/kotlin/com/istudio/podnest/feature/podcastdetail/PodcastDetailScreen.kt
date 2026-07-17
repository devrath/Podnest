package com.istudio.podnest.feature.podcastdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.core.ui.components.EpisodeRow
import com.istudio.podnest.core.ui.components.ErrorView
import com.istudio.podnest.core.ui.components.LoadingView
import com.istudio.podnest.core.ui.theme.PodnestAccentGradient
import com.istudio.podnest.core.ui.theme.PodnestAccentPurple
import com.istudio.podnest.core.ui.theme.PodnestTextDim

@Composable
fun PodcastDetailScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PodcastDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val podcast = uiState.podcast

    when {
        uiState.isLoading && podcast == null -> LoadingView(modifier)
        uiState.errorMessage != null && podcast == null ->
            ErrorView(message = uiState.errorMessage.orEmpty(), onRetry = {}, modifier = modifier)

        podcast != null -> LazyColumn(modifier = modifier, contentPadding = PaddingValues(bottom = 24.dp)) {
            item {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                PodcastHeader(
                    podcast = podcast,
                    isSubscribed = uiState.isSubscribed,
                    onSubscribeToggle = viewModel::onSubscribeToggle,
                )
            }
            items(uiState.episodes, key = { it.id }) { episode ->
                EpisodeRow(episode = episode, onClick = viewModel::onEpisodeClick)
            }
        }
    }
}

@Composable
private fun PodcastHeader(
    podcast: Podcast,
    isSubscribed: Boolean,
    onSubscribeToggle: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(podcast.artworkUrl)
                .crossfade(250)
                .build(),
            contentDescription = podcast.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(140.dp)
                .background(PodnestAccentGradient, RoundedCornerShape(24.dp)),
        )
        Text(
            text = podcast.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 14.dp),
        )
        Text(
            text = podcast.author,
            style = MaterialTheme.typography.bodyMedium,
            color = PodnestTextDim,
        )

        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (isSubscribed) {
                OutlinedButton(onClick = onSubscribeToggle) {
                    Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                    Text(text = " Subscribed", modifier = Modifier.padding(start = 4.dp))
                }
            } else {
                Button(
                    onClick = onSubscribeToggle,
                    colors = ButtonDefaults.buttonColors(containerColor = PodnestAccentPurple),
                ) {
                    Text("＋ Subscribe")
                }
            }
        }

        if (podcast.description.isNotBlank()) {
            Text(
                text = podcast.description,
                style = MaterialTheme.typography.bodyMedium,
                color = PodnestTextDim,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}
