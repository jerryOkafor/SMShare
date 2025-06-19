package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.config.SMShareConfig
import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.network.response.TokenResponse
import com.jerryokafor.smshare.core.network.util.urlEncode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import org.jetbrains.compose.resources.DrawableResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_linkedin

class LinkedInChannelConfig(
    private val httpClient: HttpClient,
    override val name: String = "LinkedIn",
    override val description: String = "Profile or Page",
    override val icon: DrawableResource = Res.drawable.ic_linkedin,
) : ChannelConfig {
    private val oAuth2BaseUrl: String = "https://www.linkedin.com/oauth/v2/authorization"

    private val accessTokenBaseUrl: String = "https://www.linkedin.com/oauth/v2/accessToken"

    private val scope: List<String> = listOf("profile", "email", "w_member_social")

    private val clientId: String = SMShareConfig.linkedInClientId

    private val clientSecret = SMShareConfig.linkedInClientSecret

    override val accountType: AccountType
        get() = AccountType.LINKEDIN

    override fun createOAuthUrl(
        state: String,
        challenge: String,
        redirectUrl: String,
    ): String = oAuth2BaseUrl + "?response_type=code" +
        "&client_id=$clientId" +
        "&scope=${urlEncode(scope.joinToString(" "))}" +
        "&state=$challenge" +
        "&redirect_uri=$redirectUrl"

    override suspend fun exchangeCodeForAccessToken(
        code: String,
        challenge: String,
        redirectUrl: String,
    ): TokenResponse {
        val response = httpClient
            .post(accessTokenBaseUrl) {
                header("content-type", "application/x-www-form-urlencoded")
                setBody(
                    "grant_type=authorization_code" +
                        "&code=$code" +
                        "&client_id=$clientId" +
                        "&client_secret=$clientSecret" +
                        "&redirect_uri=$redirectUrl",
                )
            }

        val textResponse = response.bodyAsText()
        println("response: $response, \ntextResponse: $textResponse")
        return response.body<TokenResponse>()
    }
}
