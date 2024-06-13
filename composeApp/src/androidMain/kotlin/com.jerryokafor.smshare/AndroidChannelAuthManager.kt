/*
 * MIT License
 *
 * Copyright (c) 2024 SM Share Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
    override var currentChannelConfig: ChannelConfig? = null

    override suspend fun authenticateUser(channelConfig: ChannelConfig) {
        currentChannelConfig = channelConfig

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
