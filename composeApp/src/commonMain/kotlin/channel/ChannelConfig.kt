package channel

import model.TokenResponse

/**
 * Interface for channel configuration, represents various social media channels
 *
 */
interface ChannelConfig {
    val name: String
    val description: String
    val icon: String

    fun createLoginUrl(redirectUrl: String, challenge: String): String

    suspend fun getToken(code: String, redirectUrl: String): TokenResponse

}

