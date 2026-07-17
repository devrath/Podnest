package com.istudio.podnest.domain.repository

import com.istudio.podnest.core.model.Podcast
import kotlinx.coroutines.flow.Flow

/**
 * Fully offline-first — backed only by `:core:database`, never the network.
 * The Library screen (`:feature:library`) observes [observeSubscriptions]
 * directly; Podcast Detail observes [observeIsSubscribed] to drive its
 * Subscribe/Unsubscribe button.
 */
interface SubscriptionRepository {
    fun observeSubscriptions(): Flow<List<Podcast>>
    fun observeIsSubscribed(feedId: Long): Flow<Boolean>
    suspend fun subscribe(podcast: Podcast)
    suspend fun unsubscribe(feedId: Long)
}
