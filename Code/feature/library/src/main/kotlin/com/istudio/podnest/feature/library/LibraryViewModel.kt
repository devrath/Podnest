package com.istudio.podnest.feature.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.istudio.podnest.domain.repository.SubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    subscriptionRepository: SubscriptionRepository,
) : ViewModel() {

    val uiState: StateFlow<LibraryUiState> = subscriptionRepository.observeSubscriptions()
        .map { LibraryUiState(subscriptions = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LibraryUiState(),
        )
}
