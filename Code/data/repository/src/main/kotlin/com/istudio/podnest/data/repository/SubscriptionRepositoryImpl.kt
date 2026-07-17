package com.istudio.podnest.data.repository

import com.istudio.podnest.core.database.dao.SubscriptionDao
import com.istudio.podnest.core.database.entity.SubscriptionEntity
import com.istudio.podnest.core.model.Podcast
import com.istudio.podnest.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(
    private val subscriptionDao: SubscriptionDao,
) : SubscriptionRepository {

    override fun observeSubscriptions(): Flow<List<Podcast>> =
        subscriptionDao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override fun observeIsSubscribed(feedId: Long): Flow<Boolean> =
        subscriptionDao.observeIsSubscribed(feedId)

    override suspend fun subscribe(podcast: Podcast) {
        subscriptionDao.upsert(podcast.toEntity())
    }

    override suspend fun unsubscribe(feedId: Long) {
        subscriptionDao.deleteByFeedId(feedId)
    }

    private fun SubscriptionEntity.toDomain() = Podcast(
        feedId = feedId,
        title = title,
        author = author,
        description = description,
        artworkUrl = artworkUrl,
        isSubscribed = true,
    )

    private fun Podcast.toEntity() = SubscriptionEntity(
        feedId = feedId,
        title = title,
        author = author,
        description = description,
        artworkUrl = artworkUrl,
        subscribedAtEpochSeconds = System.currentTimeMillis() / 1000,
    )
}
