package com.istudio.podnest.data.repository

import com.istudio.podnest.core.common.flow.asAppResultFlow
import com.istudio.podnest.core.common.result.AppResult
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.data.remote.api.PodcastIndexApi
import com.istudio.podnest.data.remote.mapper.toDomain
import com.istudio.podnest.domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PodcastRepositoryImpl @Inject constructor(
    private val api: PodcastIndexApi,
) : PodcastRepository {

    override fun getTrendingPodcasts(max: Int): Flow<AppResult<List<Podcast>>> =
        flow {
            val response = api.getTrendingPodcasts(max = max)
            emit(response.feeds.map { it.toDomain() })
        }.asAppResultFlow()

    override fun searchPodcasts(query: String, max: Int): Flow<AppResult<List<Podcast>>> =
        flow {
            if (query.isBlank()) {
                emit(emptyList())
                return@flow
            }
            val response = api.searchPodcasts(query = query, max = max)
            emit(response.feeds.map { it.toDomain() })
        }.asAppResultFlow()

    override fun getPodcastDetail(feedId: Long): Flow<AppResult<Podcast>> =
        flow {
            val response = api.getPodcastByFeedId(feedId = feedId)
            val feed = requireNotNull(response.feed) { "Podcast not found for feedId=$feedId" }
            emit(feed.toDomain())
        }.asAppResultFlow()
}
