package com.istudio.podnest.feature.library

import com.istudio.podnest.core.model.Podcast

data class LibraryUiState(
    val subscriptions: List<Podcast> = emptyList(),
) {
    val isEmpty: Boolean get() = subscriptions.isEmpty()
}
