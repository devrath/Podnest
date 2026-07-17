package com.istudio.podnest.core.model

/**
 * Canonical, normalized podcast/feed model used across every layer of Podnest.
 * Mapped from Podcast Index's `PodcastDto` in `:data:remote` — nothing above
 * `:domain` ever touches the raw API shape directly.
 */
data class Podcast(
    val feedId: Long,
    val title: String,
    val author: String,
    val description: String,
    val artworkUrl: String,
    val siteUrl: String? = null,
    val categories: List<String> = emptyList(),
    val episodeCount: Int? = null,
    val isSubscribed: Boolean = false,
)
