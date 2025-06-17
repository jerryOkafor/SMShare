package com.jerryokafor.smshare

import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfig
import com.jerryokafor.smshare.core.config.SMShareConfig
import io.ktor.util.encodeBase64
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.CC_SHA256
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import kotlin.random.Random

fun base64UrlEncode(bytes: ByteArray): String {
    val base64 = bytes.encodeBase64()
    return base64
        .replace("+", "-")
        .replace("/", "_")
        .replace("=", "")
}

class IOSChannelAuthManager : ChannelAuthManager {
    override lateinit var challenge: String
    override var channelConfig: ChannelConfig? = null

    @Suppress("MagicNumber")
    override fun getState(): String {
        val bytes = ByteArray(32).apply {
            Random.nextBytes(this)
        }
        return base64UrlEncode(bytes)
    }

    override suspend fun authenticateUser(channelConfig: ChannelConfig) {
        this.channelConfig = channelConfig

        val oauth2Url = channelConfig.createOAuthUrl(
            redirectUrl = getRedirectUrl(),
            state = getState(),
            challenge = getChallenge(),
        )

        NSURL.URLWithString(oauth2Url)?.let { nsUrl ->
            UIApplication.sharedApplication.openURL(
                url = nsUrl,
                options = emptyMap<Any?, Any?>(),
                completionHandler = { success ->
                    println("URL launch success: $success")
                },
            )
        }
    }

    override suspend fun getChallenge(): String = if (::challenge.isInitialized) {
        this.challenge
    } else {
        val verifier = createVerifier()
        val sha256Digest = sha256(verifier.encodeToByteArray())

        val challenge = base64UrlEncode(sha256Digest)
        this.challenge = challenge

        challenge
    }

    @Suppress("MagicNumber")
    private fun createVerifier(): String {
        val bytes = ByteArray(32).apply {
            Random.nextBytes(this)
        }
        return base64UrlEncode(bytes)
    }

    override suspend fun getRedirectUrl(): String = SMShareConfig.redirectUrl
}

fun sha256(input: ByteArray): ByteArray {
    val digest = UByteArray(CC_SHA256_DIGEST_LENGTH)
    input.usePinned { pinnedInput ->
        digest.usePinned { pinnedDigest ->
            CC_SHA256(
                pinnedInput.addressOf(0),
                input.size.convert(),
                pinnedDigest.addressOf(0),
            )
        }
    }
    return digest.toByteArray()
}
