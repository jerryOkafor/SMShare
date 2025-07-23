package com.jerryokafor.smshare.core.network.util

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA256
import dev.whyoleg.cryptography.random.CryptographyRandom
import io.ktor.util.encodeBase64
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

expect fun urlEncode(value: String): String

expect fun urlDecode(value: String): String

fun oauthChallengeVerify(): String {
    val bytes = CryptographyRandom.nextBytes(32)
    return bytes.encodeBase64()
}

suspend fun oauthChallenge(verifier: String): String {
    val bytes: ByteArray = verifier.toByteArray(Charsets.UTF_8)
    return CryptographyProvider.Default
        .get(SHA256)
        .hasher()
        .hash(bytes)
        .encodeBase64()
}
