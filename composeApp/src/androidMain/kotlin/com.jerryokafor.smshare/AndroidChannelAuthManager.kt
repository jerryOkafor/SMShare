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
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfig
import com.jerryokafor.smshare.core.config.SMShareConfig
import org.apache.commons.codec.binary.Base64
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.SecureRandom

class AndroidChannelAuthManager(
    private val context: Context,
) : ChannelAuthManager {
    override lateinit var challenge: String
    override var channelConfig: ChannelConfig? = null

    @Suppress("MagicNumber")
    override fun getState(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return java.util.Base64
            .getUrlEncoder()
            .withoutPadding()
            .encodeToString(code)
    }

    override suspend fun authenticateUser(channelConfig: ChannelConfig) {
        this.channelConfig = channelConfig

        val redirectUrl = getRedirectUrl()
        val challenge = getChallenge()

        val encodedRedirectUrl = URLEncoder.encode(redirectUrl, "utf-8")
        val encodedChallenge = URLEncoder.encode(challenge, "utf-8")

        val oauth2Url = channelConfig.createOAuthUrl(
            redirectUrl = encodedRedirectUrl,
            state = getState(),
            challenge = encodedChallenge,
        )

        val intent: CustomTabsIntent =
            CustomTabsIntent
                .Builder()
                .apply {
                    setCloseButtonIcon(
                        BitmapFactory.decodeResource(context.resources, R.drawable.ic_arrow_back),
                    )
                }.build()
                .apply {
                    intent.flags = FLAG_ACTIVITY_NEW_TASK
                }

        intent.launchUrl(context, oauth2Url.toUri())
    }

    override suspend fun getChallenge(): String = if (::challenge.isInitialized) {
        this.challenge
    } else {
        val bytes: ByteArray = createVerifier().toByteArray(Charsets.US_ASCII)
        val md = MessageDigest.getInstance("SHA-256")
        md.update(bytes, 0, bytes.size)
        val digest = md.digest()
        val challenge = Base64.encodeBase64URLSafeString(digest)

        this.challenge = challenge
        challenge
    }

    override suspend fun getRedirectUrl(): String = SMShareConfig.redirectUrl

    @Suppress("MagicNumber")
    private fun createVerifier(): String {
        val sr = SecureRandom()
        val code = ByteArray(32)
        sr.nextBytes(code)
        return java.util.Base64
            .getUrlEncoder()
            .withoutPadding()
            .encodeToString(code)
    }
}
