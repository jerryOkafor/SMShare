package channel

import model.TokenResponse
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.ic_twitter

class XChannelConfig @OptIn(ExperimentalResourceApi::class) constructor(
    override val name: String = "Twitter/X",
    override val description: String = "Profile",
    override val icon: DrawableResource = Res.drawable.ic_twitter
) : ChannelConfig {
    override fun createLoginUrl(redirectUrl: String, challenge: String): String = ""

    override suspend fun getToken(code: String, redirectUrl: String): TokenResponse {
        TODO("Not yet implemented")
    }
}