import channel.ChannelConfig
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.posix.memcpy

actual class Platform actual constructor() {
    actual val platform: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}


actual class AuthManager private actual constructor() {
    lateinit var name: String
        private set

    constructor(name: String = "") : this() {
        this.name = name
    }

    actual fun authenticateUser(channelConfig: ChannelConfig) {
        println("Authenticating user with $channelConfig")
        val redirectUrl = "https://jerryokafor.com/smshare/auth/callback"
        val challenge = "123456NJSANLENLNE"
        val oauth2Url = channelConfig.createLoginUrl(
            redirectUrl = redirectUrl,
            challenge = challenge
        )
        NSURL.URLWithString(oauth2Url)?.let {
            val result = UIApplication.sharedApplication.openURL(it)
            println("Launching: $oauth2Url | Result: $result")
        }

    }

    actual var currentChannel: ChannelConfig?
        get() = TODO("Not yet implemented")
        set(value) {}

}


@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray = ByteArray(this@toByteArray.length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
    }
}
