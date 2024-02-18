package channel

import model.TokenResponse

class FacebookChannelConfig(
    override val name: String = "Facebook",
    override val description: String = "Page or Group",
    override val icon: String = "ic_facebook.xml"
) : ChannelConfig {
    override fun createLoginUrl(redirectUrl: String, challenge: String): String = ""

    override suspend fun getToken(code: String, redirectUrl: String): TokenResponse {
        TODO("Not yet implemented")
    }
}