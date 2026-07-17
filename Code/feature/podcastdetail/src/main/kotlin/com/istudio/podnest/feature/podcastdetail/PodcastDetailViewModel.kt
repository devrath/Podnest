package com.istudio.podnest.feature.podcastdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.istudio.podnest.core.common.result.AppResult
import com.istudio.podnest.core.model.Episode
import com.istudio.podnest.core.playback.PlaybackLauncher
import com.istudio.podnest.domain.repository.EpisodeRepository
import com.istudio.podnest.domain.repository.PodcastRepository
import com.istudio.podnest.domain.repository.SubscriptionRepository
import com.istudio.podnest.feature.podcastdetail.navigation.PodcastDetailArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val podcastRepository: PodcastRepository,
    private val episodeRepository: EpisodeRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val playbackLauncher: PlaybackLauncher,
) : ViewModel() {

    private val feedId: Long = PodcastDetailArgs(savedStateHandle).feedId

    val uiState: StateFlow<PodcastDetailUiState> = combine(
        podcastRepository.getPodcastDetail(feedId),
        episodeRepository.getEpisodes(feedId),
        subscriptionRepository.observeIsSubscribed(feedId),
    ) { podcastResult, episodesResult, isSubscribed ->
        PodcastDetailUiState(
            isLoading = podcastResult is AppResult.Loading || episodesResult is AppResult.Loading,
            podcast = (podcastResult as? AppResult.Success)?.data,
            episodes = (episodesResult as? AppResult.Success)?.data.orEmpty(),
            isSubscribed = isSubscribed,
            errorMessage = (podcastResult as? AppResult.Error)?.message
                ?: (episodesResult as? AppResult.Error)?.message,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PodcastDetailUiState(),
    )

    fun onEpisodeClick(episode: Episode) {
        playbackLauncher.playEpisode(episode)
    }

    fun onSubscribeToggle() {
        val current = uiState.value
        val podcast = current.podcast ?: return
        viewModelScope.launch {
            if (current.isSubscribed) {
                subscriptionRepository.unsubscribe(feedId)
            } else {
                subscriptionRepository.subscribe(podcast)
            }
        }
    }
}
