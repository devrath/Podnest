package com.istudio.podnest.core.database.di

import android.content.Context
import androidx.room.Room
import com.istudio.podnest.core.database.PodnestDatabase
import com.istudio.podnest.core.database.dao.SubscriptionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePodnestDatabase(@ApplicationContext context: Context): PodnestDatabase =
        Room.databaseBuilder(context, PodnestDatabase::class.java, PodnestDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideSubscriptionDao(database: PodnestDatabase): SubscriptionDao = database.subscriptionDao()
}
