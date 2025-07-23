package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.config.SMShareConfig
import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.model.UserProfile
import com.jerryokafor.smshare.core.network.response.FacebookUserProfilePictureResponse
import com.jerryokafor.smshare.core.network.response.FacebookUserProfileResponse
import com.jerryokafor.smshare.core.network.response.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import org.jetbrains.compose.resources.DrawableResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_facebook

//https://developers.facebook.com/docs/facebook-login/guides/advanced/manual-flow

class FacebookChannelConfig(
    private val httpClient: HttpClient,
    override val name: String = "Facebook",
    override val description: String = "Page or Group",
    override val icon: DrawableResource = Res.drawable.ic_facebook,
) : ChannelConfigResource {
    override val accountType: AccountType
        get() = AccountType.FACEBOOK

    val clientId: String
        get() = SMShareConfig.facebookClientId

    val clientSecret: String
        get() = SMShareConfig.facebookClientSecret

    private val graphQLBaseUrl: String = "https://graph.facebook.com/v23.0"

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
            urlString = "$graphQLBaseUrl/oauth/access_token" +
                    "?client_id=$clientId" +
                    "&redirect_uri=$redirectUrl" +
                    "&client_secret=$clientSecret" +
                    "&code=$code",
        ).body<TokenResponse>()

    override suspend fun userProfile(accessToken: String): UserProfile {
        val profileResponse = httpClient.get(urlString = graphQLBaseUrl) {
            url {
                appendPathSegments("me")
                parameters.append(
                    "fields",
                    listOf(
                        "id",
                        "name",
                        "email",
                        "first_name",
                        "last_name"
                    ).joinToString(separator = ",")
                )

                parameters.append("access_token", accessToken)
                trailingQuery = true
            }
        }.body<FacebookUserProfileResponse>()

        val userId = profileResponse.id
        val profilePictureResponse = httpClient.get(urlString = graphQLBaseUrl) {
            url {
                appendPathSegments("$userId/picture")
                parameters.append("access_token", accessToken)
                parameters.append("redirect", "false") //avoid redirecting and return json
                parameters.append("type", "large") //return large size
                trailingQuery = true
            }
        }.body<FacebookUserProfilePictureResponse>()

        return UserProfile(
            subjectId = profileResponse.id!!,
            name = profileResponse.name,
            givenName = profileResponse.firstName,
            familyName = profileResponse.lastName,
            picture = profilePictureResponse.data?.url,
            email = profileResponse.email,
            locale = null
        )
    }
}
