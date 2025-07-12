package com.jerryokafor.smshare.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XUserLookUpMeResponse(
    val data: XUserLookUpMe? = null
)


@Serializable
data class XUserLookUpMe(
    val id: String? = null,
    val username: String? = null,
    
    @SerialName("confirmed_email")
    val name: String? = null,
    val email: String? = null,

    @SerialName("profile_image_url")
    val profileImageUrl: String? = null,
)
