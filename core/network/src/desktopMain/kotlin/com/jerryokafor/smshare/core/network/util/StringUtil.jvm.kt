package com.jerryokafor.smshare.core.network.util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

actual fun urlEncode(value: String): String = URLEncoder
    .encode(value, StandardCharsets.UTF_8.toString())
    .replace("+", "%20")

actual fun urlDecode(value: String): String =
    URLDecoder.decode(value, StandardCharsets.UTF_8.toString())
