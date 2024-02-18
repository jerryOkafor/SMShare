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