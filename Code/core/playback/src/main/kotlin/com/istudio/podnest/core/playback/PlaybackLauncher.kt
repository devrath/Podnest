package com.istudio.podnest.core.playback

import com.istudio.podnest.core.model.Episode

/**
 * The single door every feature module uses to start playback.
 *
 * ★ This is the seam for the future `:playerkit` module. ★
 *
 * No feature module (`:feature:discover`, `:feature:search`,
 * `:feature:podcastdetail`, `:feature:library`) is ever allowed to depend on
 * a real player implementation directly — they only know about this
 * interface, injected via Hilt. Today it's bound to [ToastPlaybackLauncher].
 *
 * When `:playerkit` (built around Media3 ExoPlayer) is added later, the only
 * change required anywhere in the app is:
 *   1. Add a `playerkit` module implementing this interface (e.g. wired to a
 *      `MediaSessionService` + `MediaController`, per the Podnest design doc).
 *   2. Swap the `@Binds` target in [com.istudio.podnest.core.playback.di.PlaybackModule]
 *      from `ToastPlaybackLauncher` to the new real implementation.
 * Every feature module, screen, and click handler needs zero changes.
 */
interface PlaybackLauncher {
    fun playEpisode(episode: Episode)
}
