package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.config.SMShareConfig
import com.jerryokafor.smshare.core.domain.mapping.toUserProfile
import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.model.UserProfile
import com.jerryokafor.smshare.core.network.response.TokenResponse
import com.jerryokafor.smshare.core.network.response.XUserLookUpMeResponse
import com.jerryokafor.smshare.core.network.util.urlEncode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.jetbrains.compose.resources.DrawableResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_twitter

// https://docs.x.com/resources/fundamentals/authentication/oauth-2-0/overview
class XChannelConfig(
    private val httpClient: HttpClient,
    override val name: String = "Twitter/X",
    override val description: String = "Profile",
    override val icon: DrawableResource = Res.drawable.ic_twitter,
) : ChannelConfigResource {
    private val oAuth2BaseUrl: String = "https://x.com/i/oauth2/authorize"

    private val accessTokenBaseUrl: String = "https://api.x.com/2/oauth2/token"

    @Suppress("UnusedPrivateProperty")
    private val authTokenRevokeBasedUrl: String = "https://api.x.com/2/oauth2/revoke"
    private val scope: List<String> =
        listOf("users.email", "users.read", "tweet.read", "follows.read", "follows.write")
    private val clientId: String = SMShareConfig.xClientId

    override val accountType: AccountType
        get() = AccountType.TWITTER_X

    override fun createOAuthUrl(
        state: String,
        challenge: String,
        redirectUrl: String,
    ): String = oAuth2BaseUrl + "?response_type=code" +
        "&client_id=$clientId" +
        "&scope=${urlEncode(scope.joinToString(" "))}" +
        "&state=$state" +
        "&code_challenge=$challenge" +
        "&redirect_uri=$redirectUrl" +
        "&code_challenge_method=S256"

    override suspend fun exchangeCodeForAccessToken(
        code: String,
        challenge: String,
        redirectUrl: String,
    ): TokenResponse = httpClient
        .post(accessTokenBaseUrl) {
            header("content-type", "application/x-www-form-urlencoded")
            setBody(
                "grant_type=authorization_code" +
                    "&code=$code" +
                    "&client_id=$clientId" +
                    "&code_verifier=$challenge" +
                    "&redirect_uri=$redirectUrl",
            )
        }.body<TokenResponse>()

    override suspend fun userProfile(accessToken: String): UserProfile {
        val response = httpClient.get("https://api.x.com/2/users/me") {
            header("Authorization", "Bearer $accessToken")
            url {
                parameters.append(
                    "user.fields",
                    listOf(
                        "id",
                        "name",
                        "description",
                        "username",
                        "profile_image_url",
                        "confirmed_email"
                    ).joinToString(",")
                )
                trailingQuery = true
            }
        }.body<XUserLookUpMeResponse>()

        return response.toUserProfile()
    }
}
