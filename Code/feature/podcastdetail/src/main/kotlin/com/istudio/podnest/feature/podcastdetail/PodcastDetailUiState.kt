package com.istudio.podnest.feature.podcastdetail

import com.istudio.podnest.core.model.Episode
import com.istudio.podnest.core.model.Podcast

data class PodcastDetailUiState(
    val isLoading: Boolean = true,
    val podcast: Podcast? = null,
    val episodes: List<Episode> = emptyList(),
    val isSubscribed: Boolean = false,
    val errorMessage: String? = null,
)
