package com.istudio.podnest.core.model

/**
 * Canonical, normalized episode model. `audioUrl` is what will eventually be
 * handed to `playerkit` as a MediaItem source once that module exists — for
 * now it's only consumed by [com.istudio.podnest.core.playback.PlaybackLauncher].
 */
data class Episode(
    val id: Long,
    val feedId: Long,
    val guid: String,
    val title: String,
    val description: String,
    val audioUrl: String,
    val imageUrl: String?,
    val durationSeconds: Long,
    val publishedAtEpochSeconds: Long,
    val podcastTitle: String = "",
)
