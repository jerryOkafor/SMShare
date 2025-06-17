package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.network.response.TokenResponse
import org.jetbrains.compose.resources.DrawableResource

/**
 * Interface for channel configuration, represents various social media platforms
 * that a user may want to add to the app.
 *
 */
interface ChannelConfig {
    val accountType: AccountType

    val name: String

    val description: String

    val icon: DrawableResource

    fun createOAuthUrl(
        state: String,
        challenge: String,
        redirectUrl: String,
    ): String

    suspend fun exchangeCodeForAccessToken(
        code: String,
        challenge: String,
        redirectUrl: String,
    ): TokenResponse
}
