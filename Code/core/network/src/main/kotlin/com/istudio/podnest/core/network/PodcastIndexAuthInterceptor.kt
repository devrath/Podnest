package com.istudio.podnest.core.network

import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest

/**
 * Podcast Index uses an Amazon-style request signature on every authenticated
 * endpoint: `Authorization = sha1(apiKey + apiSecret + unixTime)`, alongside
 * `X-Auth-Key` / `X-Auth-Date` / `User-Agent` headers.
 *
 * See: https://podcastindex-org.github.io/docs-api/ → "Authentication Details"
 *
 * Constructed explicitly (not `@Inject constructor`) in [di.NetworkModule] since
 * the key/secret come from BuildConfig, not the Hilt graph.
 */
class PodcastIndexAuthInterceptor(
    private val apiKey: String,
    private val apiSecret: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val unixTime = (System.currentTimeMillis() / 1000).toString()
        val authHeader = sha1Hex(apiKey + apiSecret + unixTime)

        val authenticatedRequest = chain.request().newBuilder()
            .header("User-Agent", "Podnest/1.0")
            .header("X-Auth-Date", unixTime)
            .header("X-Auth-Key", apiKey)
            .header("Authorization", authHeader)
            .build()

        return chain.proceed(authenticatedRequest)
    }

    private fun sha1Hex(input: String): String {
        val digest = MessageDigest.getInstance("SHA-1").digest(input.toByteArray(Charsets.UTF_8))
        return digest.joinToString(separator = "") { byte -> "%02x".format(byte) }
    }
}
