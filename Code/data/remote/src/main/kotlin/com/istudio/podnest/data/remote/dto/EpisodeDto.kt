package com.istudio.podnest.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Raw shape of a Podcast Index "episode" item, as returned by
 * `/episodes/byfeedid`, `/recent/episodes`, etc.
 */
@Serializable
data class EpisodeDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String = "",
    @SerialName("description") val description: String? = null,
    @SerialName("guid") val guid: String? = null,
    @SerialName("feedId") val feedId: Long = 0L,
    @SerialName("feedTitle") val feedTitle: String? = null,
    @SerialName("feedImage") val feedImage: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("enclosureUrl") val enclosureUrl: String = "",
    @SerialName("duration") val duration: Long? = null,
    @SerialName("datePublished") val datePublished: Long? = null,
)

@Serializable
data class EpisodesResponseDto(
    @SerialName("status") val status: String? = null,
    @SerialName("items") val items: List<EpisodeDto> = emptyList(),
    @SerialName("count") val count: Int = 0,
)
