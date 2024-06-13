package com.jerryokafor.smshare

import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfig
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IOSChannelAuthManager : ChannelAuthManager {
    override var currentChannelConfig: ChannelConfig? = null

    override suspend fun authenticateUser(channelConfig: ChannelConfig) {
        currentChannelConfig = channelConfig
        val oauth2Url = channelConfig.createLoginUrl(
            redirectUrl = getRedirectUrl(),
            challenge = getChallenge(),
        )

        NSURL.URLWithString(oauth2Url)?.let {
            val result = UIApplication.sharedApplication.openURL(it)
            println("Launching: $oauth2Url | Result: $result")
        }
    }

    override suspend fun getChallenge(): String = "123456NJSANLENLNE"

    override suspend fun getRedirectUrl(): String = "https://jerryokafor.com/smshare/auth/callback"
}
