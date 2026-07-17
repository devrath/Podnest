import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

// Podcast Index API credentials, read from the gitignored root local.properties file.
// See README.md → "API credentials" for setup instructions.
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}
val podcastIndexApiKey: String = localProperties.getProperty("PODCASTINDEX_API_KEY", "")
val podcastIndexApiSecret: String = localProperties.getProperty("PODCASTINDEX_API_SECRET", "")

android {
    namespace = "com.istudio.podnest.core.network"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "PODCASTINDEX_API_KEY", "\"$podcastIndexApiKey\"")
        buildConfigField("String", "PODCASTINDEX_API_SECRET", "\"$podcastIndexApiSecret\"")
        buildConfigField("String", "PODCASTINDEX_BASE_URL", "\"https://api.podcastindex.org/api/1.0/\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // NOTE: core:network is intentionally generic infra (OkHttp/Retrofit/Json wiring).
    // It must NOT depend on :data:remote — that would invert the dependency direction.
    // :data:remote depends on core:network instead, and builds the concrete
    // PodcastIndexApi from the Retrofit instance provided here.

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}
