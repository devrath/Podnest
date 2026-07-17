pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Podnest"

include(":app")

// ---- core ----
include(":core:common")
include(":core:model")
include(":core:ui")
include(":core:network")
include(":core:database")
include(":core:playback")

// ---- domain ----
include(":domain")

// ---- data ----
include(":data:remote")
include(":data:repository")

// ---- feature ----
include(":feature:discover")
include(":feature:search")
include(":feature:podcastdetail")
include(":feature:library")

// NOTE: ":playerkit" will be added here once the ExoPlayer/Media3 implementation
// module is created. It should only ever be depended on by :app and, later,
// a dedicated ":feature:player-ui" module — never by other feature modules.
