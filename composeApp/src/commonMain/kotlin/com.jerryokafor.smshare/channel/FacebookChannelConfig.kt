package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.config.SMShareConfig
import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.network.response.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.jetbrains.compose.resources.DrawableResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_facebook

class FacebookChannelConfig(
    private val httpClient: HttpClient,
    override val name: String = "Facebook",
    override val description: String = "Page or Group",
    override val icon: DrawableResource = Res.drawable.ic_facebook,
) : ChannelConfig {
    override val accountType: AccountType
        get() = AccountType.FACEBOOK

    val clientId: String
        get() = SMShareConfig.facebookClientId

    val clientSecret: String
        get() = SMShareConfig.facebookClientSecret

    override fun createOAuthUrl(
        state: String,
        challenge: String,
        redirectUrl: String,
    ): String = "https://www.facebook.com/v23.0/dialog/oauth" +
        "?client_id=$clientId" +
        "&redirect_uri=$redirectUrl" +
        "&state=$state"

    override suspend fun exchangeCodeForAccessToken(
        code: String,
        challenge: String,
        redirectUrl: String,
    ): TokenResponse = httpClient
        .get(
            "https://graph.facebook.com/v23.0/oauth/access_token" +
                "?client_id=$clientId" +
                "&redirect_uri=$redirectUrl" +
                "&client_secret=$clientSecret" +
                "&code=$code",
        ).body<TokenResponse>()
}
