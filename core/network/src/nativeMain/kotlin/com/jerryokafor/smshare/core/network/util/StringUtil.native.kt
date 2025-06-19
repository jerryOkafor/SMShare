package com.jerryokafor.smshare.core.network.util

import platform.Foundation.NSCharacterSet
import platform.Foundation.NSMutableCharacterSet
import platform.Foundation.NSString
import platform.Foundation.URLQueryAllowedCharacterSet
import platform.Foundation.stringByAddingPercentEncodingWithAllowedCharacters
import platform.Foundation.stringByRemovingPercentEncoding

actual fun urlEncode(value: String): String {
    val allowed = NSMutableCharacterSet.characterSetWithCharactersInString("")
    allowed.formUnionWithCharacterSet(NSCharacterSet.URLQueryAllowedCharacterSet)
    allowed.removeCharactersInString("+=&")
    return (value as NSString).stringByAddingPercentEncodingWithAllowedCharacters(allowed)
        ?: throw IllegalArgumentException("Failed to encode URL")
}

actual fun urlDecode(value: String): String = (value as NSString)
    .stringByRemovingPercentEncoding
    ?.replace("+", " ")
    ?: throw IllegalArgumentException("Failed to decode URL")
