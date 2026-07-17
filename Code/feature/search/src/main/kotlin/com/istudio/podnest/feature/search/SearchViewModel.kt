package com.istudio.podnest.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.istudio.podnest.core.common.result.AppResult
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.domain.repository.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository,
) : ViewModel() {

    private val query = MutableStateFlow("")

    private val searchResults: StateFlow<AppResult<List<Podcast>>> = query
        .debounce(350)
        .distinctUntilChanged()
        .flatMapLatest { text ->
            if (text.isBlank()) {
                flowOf(AppResult.Success(emptyList()))
            } else {
                podcastRepository.searchPodcasts(text)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppResult.Loading)

    val uiState: StateFlow<SearchUiState> = combine(
        query,
        searchResults,
    ) { text, result ->
        SearchUiState(
            query = text,
            isLoading = result is AppResult.Loading && text.isNotBlank(),
            results = (result as? AppResult.Success)?.data.orEmpty(),
            errorMessage = (result as? AppResult.Error)?.message,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SearchUiState())

    fun onQueryChange(text: String) {
        query.update { text }
    }
}
