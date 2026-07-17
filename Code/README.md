# Podnest

An Android podcast app built to showcase Media3 ExoPlayer engineering, backed by the [Podcast Index API](https://podcastindex-org.github.io/docs-api/). MVVM, multi-module, Jetpack Compose.

## Status

This is the **app shell**: Discover, Search, Podcast Detail, and Library screens are fully wired to the real Podcast Index API and to a local Room-backed subscriptions store. Playback is intentionally **not** implemented yet — every "play this episode" action shows a toast instead. See [Playback (`:playerkit`)](#playback-playerkit) below.

## Setup

1. Open the project root in Android Studio and let it sync.
2. Add your Podcast Index API credentials to the (gitignored) root `local.properties`:
   ```properties
   PODCASTINDEX_API_KEY=your_key_here
   PODCASTINDEX_API_SECRET=your_secret_here
   ```
   Get a free key/secret at https://api.podcastindex.org/. `PODCASTINDEX_API_KEY` is already set for this project; **`PODCASTINDEX_API_SECRET` is still blank — the app will fail auth (HTTP 401) on every request until you add it.**
3. Run the `app` configuration on a device/emulator (minSdk 24).

If Android Studio flags the `ksp` version in `gradle/libs.versions.toml` as mismatched with the Kotlin version, accept its suggested patch bump — it was set to a best-guess value (`2.2.10-1.0.4`) since it couldn't be verified against a live Gradle sync in the environment this was scaffolded in.

## Module map

```
app                      composition root: Hilt app, MainActivity, bottom-nav NavHost
                          (the only module allowed to see every feature module at once)

core/
  common                 AppResult<T> wrapper, DispatcherProvider          [pure Kotlin]
  model                  Podcast, Episode domain models                   [pure Kotlin]
  ui                     Podnest theme (dark, gradient accent) + shared composables
  network                OkHttp/Retrofit/Json wiring + Podcast Index HMAC auth interceptor
  database               Room — subscriptions table only (offline-first Library screen)
  playback               ★ PlaybackLauncher seam — see below ★

domain                   Repository interfaces only                       [pure Kotlin]

data/
  remote                 PodcastIndexApi (Retrofit), DTOs, DTO→domain mappers
  repository             Repository implementations (network + Room), Hilt bindings

feature/
  discover               Trending podcasts + recent episodes
  search                 Debounced podcast search
  podcastdetail          Podcast header, subscribe toggle, episode list
  library                Subscriptions grid (Room-backed, fully offline)
```

Dependency direction is one-way and acyclic: `core:model` sits at the base; `feature:*` modules depend on `domain` + `core:*` + `core:playback` but **never on each other**; `app` is the only module that sees every feature at once. This was verified by walking every `project(":...")` edge in the build — no cycles, no feature-to-feature edges.

## Playback (`:playerkit`)

Every screen that would start playback calls `PlaybackLauncher.playEpisode(episode)` — defined in `core:playback`. Today that interface is bound (via Hilt, in `core/playback/di/PlaybackModule.kt`) to `ToastPlaybackLauncher`, which just shows a toast.

When the real ExoPlayer/Media3 implementation is ready:

1. Create a new `:playerkit` module (Media3 `MediaSessionService` + `MediaController`, per the design doc's player architecture — gapless queues, `DefaultPreloadManager`, pitch-locked speed, skip-silence, chapters, offline downloads, Cast, Android Auto).
2. Add it to `settings.gradle.kts` (a placeholder comment is already there).
3. Implement `PlaybackLauncher` in `:playerkit`.
4. Change one line in `core/playback/di/PlaybackModule.kt` to bind the real implementation instead of `ToastPlaybackLauncher`.

No feature module, screen, or click handler needs to change — that's the entire point of the seam.

## Not yet implemented (by design, this pass)

- Playback (`:playerkit` — see above)
- Downloads / offline episode storage
- Dynamic feature delivery (explicitly out of scope per current requirements)
- Chapters, transcripts, value-for-value (deferred alongside playback, since they're player-adjacent)

## Architecture reference

See `Podnest_Design_Document.md` and `Podnest_Project_Structure.md` (shared earlier in this project) for the fuller architectural rationale, the Media3 feature matrix, and the original dynamic-delivery-ready module plan this was simplified from.
