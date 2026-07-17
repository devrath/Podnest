package com.istudio.podnest.core.playback.di

import com.istudio.podnest.core.playback.PlaybackLauncher
import com.istudio.podnest.core.playback.ToastPlaybackLauncher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Binds [PlaybackLauncher] to the placeholder [ToastPlaybackLauncher].
 *
 * ★ When `:playerkit` is added, this is the ONLY file that needs to change ★
 * to swap in real playback everywhere in the app:
 *
 * ```kotlin
 * @Binds
 * @Singleton
 * abstract fun bindPlaybackLauncher(impl: PlayerKitPlaybackLauncher): PlaybackLauncher
 * ```
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class PlaybackModule {

    @Binds
    @Singleton
    abstract fun bindPlaybackLauncher(impl: ToastPlaybackLauncher): PlaybackLauncher
}
