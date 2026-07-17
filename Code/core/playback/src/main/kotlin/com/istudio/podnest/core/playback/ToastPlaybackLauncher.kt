package com.istudio.podnest.core.playback

import android.content.Context
import android.widget.Toast
import com.istudio.podnest.core.model.Episode
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Placeholder [PlaybackLauncher] used until `:playerkit` exists. Shows a
 * toast instead of actually starting playback — every "play this episode"
 * action in the app (Discover, Search, Podcast Detail, Library) routes here
 * for now, entirely unaware that it's not real playback.
 */
class ToastPlaybackLauncher @Inject constructor(
    @ApplicationContext private val context: Context,
) : PlaybackLauncher {

    override fun playEpisode(episode: Episode) {
        Toast.makeText(
            context,
            "▶ \"${episode.title}\" — playerkit isn't wired up yet",
            Toast.LENGTH_SHORT,
        ).show()
    }
}
