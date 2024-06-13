package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.network.response.TokenResponse
import org.jetbrains.compose.resources.DrawableResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_twitter

class XChannelConfig(
    override val name: String = "Twitter/X",
    override val description: String = "Profile",
    override val icon: DrawableResource = Res.drawable.ic_twitter,
) : ChannelConfig {
    override val accountType: AccountType
        get() = AccountType.TWITTER_X

    override fun createLoginUrl(
        redirectUrl: String,
        challenge: String,
    ): String = ""

    override suspend fun requestAccessToken(
        code: String,
        redirectUrl: String,
    ): TokenResponse {
        TODO("Not yet implemented")
    }
}
