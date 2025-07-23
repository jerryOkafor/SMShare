package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.config.SMShareConfig
import com.jerryokafor.smshare.core.domain.mapping.toUserProfile
import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.model.UserProfile
import com.jerryokafor.smshare.core.network.response.LinkedInUserInfoResponse
import com.jerryokafor.smshare.core.network.response.TokenResponse
import com.jerryokafor.smshare.core.network.util.urlEncode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_linkedin


//Docs:
//https://learn.microsoft.com/en-us/linkedin/consumer/integrations/self-serve/share-on-linkedin?context=linkedin%2Fconsumer%2Fcontext

class LinkedInChannelConfig(
    private val httpClient: HttpClient,
    override val name: String = "LinkedIn",
    override val description: String = "Profile or Page",
    override val icon: DrawableResource = Res.drawable.ic_linkedin,
) : ChannelConfigResource {
    private val baseApiUrl = "https://api.linkedin.com/v2"

    private val oAuth2BaseUrl: String = "https://www.linkedin.com/oauth/v2/authorization"

    private val accessTokenBaseUrl: String = "https://www.linkedin.com/oauth/v2/accessToken"

    private val scope: List<String> = listOf("openid", "profile", "email", "w_member_social")

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

    override suspend fun userProfile(accessToken: String): UserProfile {
        val response = httpClient.get("$baseApiUrl/userinfo") {
            header("Authorization", "Bearer $accessToken")
        }.body<LinkedInUserInfoResponse>()

        println("response: $response")

        return response.toUserProfile()
    }

    private suspend fun postUserGeneratedContent(accessToken: String, share: SharePost) {
        val response = httpClient.post("$baseApiUrl/ugcPosts") {
            header("Authorization", "Bearer $accessToken")
            header("X-Restli-Protocol-Version", "2.0.0")
            setBody(share)
        }.body<Any>()

        println("response: $response")
    }
}

/**
 * Defines any visibility restrictions for the share.
 * */
@Serializable
enum class MemberNetworkVisibility {
    /**
     * The share will be viewable by 1st-degree connections only.
     * */
    CONNECTIONS,

    /**
     * The share will be viewable by anyone on LinkedIn.
     * */
    PUBLIC
}

@Serializable
data class SharePost(
    val author: String,
    val lifecycleState: LifecycleState = LifecycleState.PUBLISHED,
    val specificContent: ShareContent,
    val visibility: Visibility,
)

@Serializable
enum class LifecycleState {
    PUBLISHED,

}

@Serializable
data class ShareContent(
    @SerialName("com.linkedin.ugc.ShareContent")
    val ugcShareContent: UGCShareContent,
)

/**
 * Represents the media assets attached to the share
 * */
@Serializable
enum class ShareMediaCategory {

    /**
     * The share does not contain any media, and will only consist of text.
     * */
    NONE,

    /**
     *  The share contains a URL.
     *  */
    ARTICLE,

    /**
     * The Share contains an image.
     * */
    IMAGE
}

@Serializable
data class UGCShareContent(
    val shareCommentary: ShareCommentary,
    val shareMediaCategory: ShareMediaCategory = ShareMediaCategory.NONE,
    val media: List<Media> = emptyList(),
)

@Serializable
data class ShareCommentary(val text: String)

@Serializable
data class Media(
    val status: String,
    val description: Description,
    val originalUrl: String,
    val title: Title,
)

@Serializable
data class Description(val text: String)

@Serializable
data class Title(val text: String)

@Serializable
data class Visibility(
    @SerialName("com.linkedin.ugc.MemberNetworkVisibility")
    val ugcMemberNetworkVisibility: MemberNetworkVisibility = MemberNetworkVisibility.PUBLIC,
)
