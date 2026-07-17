package com.istudio.podnest.domain.repository

import com.istudio.podnest.core.common.result.AppResult
import com.istudio.podnest.core.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    /** `/episodes/byfeedid` */
    fun getEpisodes(feedId: Long, max: Int = 50): Flow<AppResult<List<Episode>>>

    /** `/recent/episodes` */
    fun getRecentEpisodes(max: Int = 20): Flow<AppResult<List<Episode>>>
}
