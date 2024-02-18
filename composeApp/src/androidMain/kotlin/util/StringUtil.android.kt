package util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

actual fun urlEncode(value: String): String {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}

actual fun urlDecode(value: String): String {
    return URLDecoder.decode(value, StandardCharsets.UTF_8.toString())
}