package com.jerryokafor.smshare.core.network.util

import platform.Foundation.NSCharacterSet
import platform.Foundation.NSString
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.stringByRemovingPercentEncoding

actual fun urlEncode(value: String): String = (value as NSString)
    .stringByAddingPercentEncodingWithAllowedCharacters(NSCharacterSet.alphanumericCharacterSet)
    ?.replace("%20", "+")
    ?: throw IllegalArgumentException("Failed to encode URL")

actual fun urlDecode(value: String): String = (value as NSString)
    .stringByRemovingPercentEncoding
    ?.replace("+", " ")
    ?: throw IllegalArgumentException("Failed to decode URL")
