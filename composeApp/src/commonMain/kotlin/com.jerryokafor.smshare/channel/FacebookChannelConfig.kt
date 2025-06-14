package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.network.response.TokenResponse
import org.jetbrains.compose.resources.DrawableResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_facebook

class FacebookChannelConfig(
    override val name: String = "Facebook",
    override val description: String = "Page or Group",
    override val icon: DrawableResource = Res.drawable.ic_facebook,
) : ChannelConfig {
    override val accountType: AccountType
        get() = AccountType.FACEBOOK

    override fun createLoginUrl(
        redirectUrl: String,
        state: String,
        challenge: String,
    ): String = ""

    override suspend fun requestAccessToken(
        code: String,
        redirectUrl: String,
        challenge: String,
    ): TokenResponse {
        TODO("Not yet implemented")
    }
}
