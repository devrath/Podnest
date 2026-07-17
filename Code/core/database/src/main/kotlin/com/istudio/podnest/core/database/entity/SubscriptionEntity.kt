package com.istudio.podnest.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A locally-persisted subscription. Podnest is offline-first for this table:
 * the Library screen reads straight from Room, never from the network.
 */
@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey val feedId: Long,
    val title: String,
    val author: String,
    val description: String,
    val artworkUrl: String,
    val subscribedAtEpochSeconds: Long,
)
