package com.istudio.podnest.core.network.di

import android.content.Context
import com.istudio.podnest.core.network.BuildConfig
import com.istudio.podnest.core.network.PodcastIndexAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

const val PODCASTINDEX_BASE_URL_QUALIFIER = "podcastIndexBaseUrl"

/**
 * Generic network infrastructure: a configured [OkHttpClient], a shared
 * [Json] instance, and a [Retrofit] pointed at the Podcast Index base URL.
 *
 * This module knows *how* to talk to Podcast Index (auth, caching, timeouts)
 * but not *what* endpoints exist — that contract lives in `:data:remote`,
 * which depends on this module and calls `retrofit.create(PodcastIndexApi::class.java)`.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CACHE_SIZE_BYTES = 10L * 1024L * 1024L // 10 MB

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideCacheDir(@ApplicationContext context: Context): File = context.cacheDir

    @Provides
    @Singleton
    fun provideOkHttpClient(cacheDir: File): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .cache(Cache(File(cacheDir, "podcastindex_http_cache"), CACHE_SIZE_BYTES))
            .addInterceptor(
                PodcastIndexAuthInterceptor(
                    apiKey = BuildConfig.PODCASTINDEX_API_KEY,
                    apiSecret = BuildConfig.PODCASTINDEX_API_SECRET,
                ),
            )
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Named(PODCASTINDEX_BASE_URL_QUALIFIER)
    fun provideBaseUrl(): String = BuildConfig.PODCASTINDEX_BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
        @Named(PODCASTINDEX_BASE_URL_QUALIFIER) baseUrl: String,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
