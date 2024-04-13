package channel

import model.TokenResponse
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Interface for channel configuration, represents various social media channels
 *
 */
@OptIn(ExperimentalResourceApi::class)
interface ChannelConfig {
    val name: String
    val description: String

    val icon: DrawableResource

    fun createLoginUrl(redirectUrl: String, challenge: String): String

    suspend fun getToken(code: String, redirectUrl: String): TokenResponse

}

