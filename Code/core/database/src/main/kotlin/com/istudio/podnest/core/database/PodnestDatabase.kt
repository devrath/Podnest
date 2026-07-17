package com.istudio.podnest.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.istudio.podnest.core.database.dao.SubscriptionDao
import com.istudio.podnest.core.database.entity.SubscriptionEntity

@Database(
    entities = [SubscriptionEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class PodnestDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao

    companion object {
        const val DATABASE_NAME = "podnest.db"
    }
}
