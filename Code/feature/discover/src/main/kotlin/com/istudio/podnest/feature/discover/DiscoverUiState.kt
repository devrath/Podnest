package com.istudio.podnest.feature.discover

import com.istudio.podnest.core.model.Episode
import com.istudio.podnest.core.model.Podcast

data class DiscoverUiState(
    val isLoading: Boolean = true,
    val trendingPodcasts: List<Podcast> = emptyList(),
    val recentEpisodes: List<Episode> = emptyList(),
    val errorMessage: String? = null,
)
