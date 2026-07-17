package com.istudio.podnest.data.remote.mapper

import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.data.remote.dto.PodcastDto

/**
 * DTO → domain mapping. This is the one place in the whole app that knows
 * the raw Podcast Index JSON shape — everything above `:data:remote` only
 * ever sees [Podcast].
 */
fun PodcastDto.toDomain(): Podcast = Podcast(
    feedId = id,
    title = title.ifBlank { "Untitled Podcast" },
    author = author ?: ownerName ?: "Unknown",
    description = description.orEmpty(),
    artworkUrl = artwork ?: image.orEmpty(),
    siteUrl = siteUrl,
    categories = categories?.values?.toList().orEmpty(),
    episodeCount = episodeCount,
)
