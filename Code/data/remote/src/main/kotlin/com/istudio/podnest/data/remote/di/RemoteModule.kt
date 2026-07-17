package com.istudio.podnest.data.remote.di

import com.istudio.podnest.data.remote.api.PodcastIndexApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun providePodcastIndexApi(retrofit: Retrofit): PodcastIndexApi =
        retrofit.create(PodcastIndexApi::class.java)
}
