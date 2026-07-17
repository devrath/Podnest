package com.istudio.podnest.data.repository

import com.istudio.podnest.core.common.flow.asAppResultFlow
import com.istudio.podnest.core.common.result.AppResult
import com.istudio.podnest.core.model.Episode
import com.istudio.podnest.data.remote.api.PodcastIndexApi
import com.istudio.podnest.data.remote.mapper.toDomain
import com.istudio.podnest.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val api: PodcastIndexApi,
) : EpisodeRepository {

    override fun getEpisodes(feedId: Long, max: Int): Flow<AppResult<List<Episode>>> =
        flow {
            val response = api.getEpisodesByFeedId(feedId = feedId, max = max)
            emit(response.items.map { it.toDomain() })
        }.asAppResultFlow()

    override fun getRecentEpisodes(max: Int): Flow<AppResult<List<Episode>>> =
        flow {
            val response = api.getRecentEpisodes(max = max)
            emit(response.items.map { it.toDomain() })
        }.asAppResultFlow()
}
