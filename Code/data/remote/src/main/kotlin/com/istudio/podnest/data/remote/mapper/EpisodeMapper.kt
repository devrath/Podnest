package com.istudio.podnest.data.remote.mapper

import com.istudio.podnest.core.model.Episode
import com.istudio.podnest.data.remote.dto.EpisodeDto

fun EpisodeDto.toDomain(): Episode = Episode(
    id = id,
    feedId = feedId,
    guid = guid ?: id.toString(),
    title = title.ifBlank { "Untitled Episode" },
    description = description.orEmpty(),
    audioUrl = enclosureUrl,
    imageUrl = image ?: feedImage,
    durationSeconds = duration ?: 0L,
    publishedAtEpochSeconds = datePublished ?: 0L,
    podcastTitle = feedTitle.orEmpty(),
)
