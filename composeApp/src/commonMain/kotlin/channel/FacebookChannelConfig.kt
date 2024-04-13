package channel

import model.TokenResponse
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_facebook

@OptIn(ExperimentalResourceApi::class)
class FacebookChannelConfig(
    override val name: String = "Facebook",
    override val description: String = "Page or Group",
    override val icon: DrawableResource = Res.drawable.ic_facebook
) : ChannelConfig {
    override fun createLoginUrl(redirectUrl: String, challenge: String): String = ""

    override suspend fun getToken(code: String, redirectUrl: String): TokenResponse {
        TODO("Not yet implemented")
    }
}