package util

import org.apache.commons.codec.binary.Base64
import java.net.URLDecoder
import java.net.URLEncoder

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom

actual fun urlEncode(value: String): String {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}

actual fun urlDecode(value: String): String {
    return URLDecoder.decode(value, StandardCharsets.UTF_8.toString())
}

actual fun oauthChallengeVerify(): String {
    val sr = SecureRandom()
    val code = ByteArray(32)
    sr.nextBytes(code)
    return Base64.encodeBase64URLSafeString(code)
}

actual fun oauthChallenge(verifier: String): String {
    val bytes: ByteArray = verifier.toByteArray(Charsets.US_ASCII)
    val md = MessageDigest.getInstance("SHA-256")
        .apply { update(bytes, 0, bytes.size) }
    val digest = md.digest()
    return Base64.encodeBase64URLSafeString(digest)
}