package com.jerryokafor.smshare

import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfig
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IOSChannelAuthManager : ChannelAuthManager {
    override lateinit var currentChallenge: String
    override var currentChannelConfig: ChannelConfig? = null

    override fun getState(): String = "123456NJSANLENLNEsndnsldnslndlkndlf"

    override suspend fun authenticateUser(channelConfig: ChannelConfig) {
        currentChannelConfig = channelConfig
        val oauth2Url = channelConfig.createLoginUrl(
            redirectUrl = getRedirectUrl(),
            state = getState(),
            challenge = getChallenge(),
        )

        NSURL.URLWithString(oauth2Url)?.let {
            val result = UIApplication.sharedApplication.openURL(it)
            println("Launching: $oauth2Url | Result: $result")
        }
    }

    override suspend fun getChallenge(): String = if (::currentChallenge.isInitialized) {
        currentChallenge
    } else {
        val code = "123456NJSANLENLNE"
        currentChallenge = code
        code
    }

    override suspend fun getRedirectUrl(): String = "https://jerryokafor.com/smshare/auth/callback"
}
