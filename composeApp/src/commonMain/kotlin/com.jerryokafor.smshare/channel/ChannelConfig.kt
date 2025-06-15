package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.network.response.TokenResponse
import org.jetbrains.compose.resources.DrawableResource

/**
 * Interface for channel configuration, represents various social media channels
 *
 */
interface ChannelConfig {
    val accountType: AccountType

    val name: String

    val description: String

    val icon: DrawableResource

    fun createLoginUrl(
        redirectUrl: String,
        state: String,
        challenge: String,
    ): String

    suspend fun requestAccessToken(
        code: String,
        redirectUrl: String,
        challenge: String,
    ): TokenResponse
}
