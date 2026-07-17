package com.istudio.podnest.data.remote.api

import com.istudio.podnest.data.remote.dto.EpisodesResponseDto
import com.istudio.podnest.data.remote.dto.PodcastByFeedIdResponseDto
import com.istudio.podnest.data.remote.dto.SearchPodcastsResponseDto
import com.istudio.podnest.data.remote.dto.TrendingPodcastsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Thin, direct mapping of the Podcast Index REST surface actually used by
 * Podnest today. Auth headers are attached transparently by
 * [com.istudio.podnest.core.network.PodcastIndexAuthInterceptor] — nothing
 * here needs to know about signing.
 *
 * Full API reference: https://podcastindex-org.github.io/docs-api/
 */
interface PodcastIndexApi {

    @GET("search/byterm")
    suspend fun searchPodcasts(
        @Query("q") query: String,
        @Query("max") max: Int = 25,
    ): SearchPodcastsResponseDto

    @GET("podcasts/trending")
    suspend fun getTrendingPodcasts(
        @Query("max") max: Int = 20,
    ): TrendingPodcastsResponseDto

    @GET("podcasts/byfeedid")
    suspend fun getPodcastByFeedId(
        @Query("id") feedId: Long,
    ): PodcastByFeedIdResponseDto

    @GET("episodes/byfeedid")
    suspend fun getEpisodesByFeedId(
        @Query("id") feedId: Long,
        @Query("max") max: Int = 50,
    ): EpisodesResponseDto

    @GET("recent/episodes")
    suspend fun getRecentEpisodes(
        @Query("max") max: Int = 20,
    ): EpisodesResponseDto
}
