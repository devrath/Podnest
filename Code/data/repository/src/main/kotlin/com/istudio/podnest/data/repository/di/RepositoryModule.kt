package com.istudio.podnest.data.repository.di

import com.istudio.podnest.data.repository.EpisodeRepositoryImpl
import com.istudio.podnest.data.repository.PodcastRepositoryImpl
import com.istudio.podnest.data.repository.SubscriptionRepositoryImpl
import com.istudio.podnest.domain.repository.EpisodeRepository
import com.istudio.podnest.domain.repository.PodcastRepository
import com.istudio.podnest.domain.repository.SubscriptionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPodcastRepository(impl: PodcastRepositoryImpl): PodcastRepository

    @Binds
    @Singleton
    abstract fun bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

    @Binds
    @Singleton
    abstract fun bindSubscriptionRepository(impl: SubscriptionRepositoryImpl): SubscriptionRepository
}
