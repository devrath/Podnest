package com.istudio.podnest.feature.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.istudio.podnest.core.common.result.AppResult
import com.istudio.podnest.core.model.Episode
import com.istudio.podnest.core.playback.PlaybackLauncher
import com.istudio.podnest.domain.repository.EpisodeRepository
import com.istudio.podnest.domain.repository.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository,
    private val episodeRepository: EpisodeRepository,
    private val playbackLauncher: PlaybackLauncher,
) : ViewModel() {

    val uiState: StateFlow<DiscoverUiState> = combine(
        podcastRepository.getTrendingPodcasts(),
        episodeRepository.getRecentEpisodes(),
    ) { trendingResult, recentResult ->
        val isLoading = trendingResult is AppResult.Loading || recentResult is AppResult.Loading
        val error = (trendingResult as? AppResult.Error)?.message
            ?: (recentResult as? AppResult.Error)?.message

        DiscoverUiState(
            isLoading = isLoading,
            trendingPodcasts = (trendingResult as? AppResult.Success)?.data.orEmpty(),
            recentEpisodes = (recentResult as? AppResult.Success)?.data.orEmpty(),
            errorMessage = error,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DiscoverUiState(),
    )

    fun onEpisodeClick(episode: Episode) {
        playbackLauncher.playEpisode(episode)
    }
}
