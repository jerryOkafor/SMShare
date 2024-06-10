package com.jerryokafor.smshare

import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfig
import java.net.URLEncoder

class AndroidChannelAuthManager(private val context: Context) : ChannelAuthManager {
    override suspend fun authenticateUser(channelConfig: ChannelConfig) {
        val redirectUrl = getRedirectUrl()
        val challenge = getChallenge()

        val encodedRedirectUrl = URLEncoder.encode(redirectUrl, "utf-8")
        val encodedChallenge = URLEncoder.encode(challenge, "utf-8")

        val oauth2Url =
            channelConfig.createLoginUrl(
                redirectUrl = encodedRedirectUrl,
                challenge = encodedChallenge,
            )

        val intent: CustomTabsIntent =
            CustomTabsIntent.Builder()
                .apply {
                    setCloseButtonIcon(
                        BitmapFactory.decodeResource(context.resources, R.drawable.ic_arrow_back),
                    )
                }
                .build()
                .apply {
                    intent.flags = FLAG_ACTIVITY_NEW_TASK
                }

        intent.launchUrl(context, Uri.parse(oauth2Url))
    }

    override suspend fun getChallenge(): String = "123456NJSANLENLNE"

    override suspend fun getRedirectUrl(): String = "http://com.jerryokafor.smshare/auth/callback"
}
