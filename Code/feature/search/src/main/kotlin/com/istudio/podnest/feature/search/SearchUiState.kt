package com.istudio.podnest.feature.search

import com.istudio.podnest.core.model.Podcast

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<Podcast> = emptyList(),
    val errorMessage: String? = null,
) {
    val hasSearched: Boolean get() = query.isNotBlank()
}
