package com.istudio.podnest.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Raw shape of a Podcast Index "feed" object, as returned by
 * `/search/byterm`, `/podcasts/trending`, `/podcasts/byfeedid`, etc.
 * See: https://podcastindex-org.github.io/docs-api/
 */
@Serializable
data class PodcastDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String = "",
    @SerialName("author") val author: String? = null,
    @SerialName("ownerName") val ownerName: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("artwork") val artwork: String? = null,
    @SerialName("url") val feedUrl: String? = null,
    @SerialName("link") val siteUrl: String? = null,
    @SerialName("episodeCount") val episodeCount: Int? = null,
    @SerialName("categories") val categories: Map<String, String>? = null,
)

@Serializable
data class SearchPodcastsResponseDto(
    @SerialName("status") val status: String? = null,
    @SerialName("feeds") val feeds: List<PodcastDto> = emptyList(),
    @SerialName("count") val count: Int = 0,
    @SerialName("description") val description: String? = null,
)

@Serializable
data class TrendingPodcastsResponseDto(
    @SerialName("status") val status: String? = null,
    @SerialName("feeds") val feeds: List<PodcastDto> = emptyList(),
    @SerialName("count") val count: Int = 0,
)

@Serializable
data class PodcastByFeedIdResponseDto(
    @SerialName("status") val status: String? = null,
    @SerialName("feed") val feed: PodcastDto? = null,
    @SerialName("description") val description: String? = null,
)
