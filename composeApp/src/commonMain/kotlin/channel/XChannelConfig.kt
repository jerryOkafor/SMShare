package channel

import model.TokenResponse

class XChannelConfig(
    override val name: String = "Twitter/X",
    override val description: String = "Profile",
    override val icon: String = "ic_twitter.xml"
) : ChannelConfig {
    override fun createLoginUrl(redirectUrl: String, challenge: String): String = ""

    override suspend fun getToken(code: String, redirectUrl: String): TokenResponse {
        TODO("Not yet implemented")
    }
}