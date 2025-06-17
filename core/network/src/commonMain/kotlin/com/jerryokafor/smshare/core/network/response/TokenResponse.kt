package com.jerryokafor.smshare.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("access_token")
    val accessToken: String? = "",
    @SerialName("expires_in")
    val expiresIn: Int? = null,
    val scope: String? = null,
    @SerialName("token_type")
    val tokenType: String? = null,
)
