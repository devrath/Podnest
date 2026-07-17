package com.istudio.podnest.domain.repository

import com.istudio.podnest.core.common.result.AppResult
import com.istudio.podnest.core.model.Podcast
import kotlinx.coroutines.flow.Flow

/**
 * Podcast/feed metadata, backed by the Podcast Index API (`:data:repository`
 * provides the implementation, merging network + the subscriptions cached in
 * `:core:database`). Pure interface here — zero Android, zero Retrofit,
 * zero Room — so it's trivially fakeable in ViewModel unit tests.
 */
interface PodcastRepository {

    /** `/podcasts/trending` */
    fun getTrendingPodcasts(max: Int = 20): Flow<AppResult<List<Podcast>>>

    /** `/search/byterm` */
    fun searchPodcasts(query: String, max: Int = 25): Flow<AppResult<List<Podcast>>>

    /** `/podcasts/byfeedid` */
    fun getPodcastDetail(feedId: Long): Flow<AppResult<Podcast>>
}
