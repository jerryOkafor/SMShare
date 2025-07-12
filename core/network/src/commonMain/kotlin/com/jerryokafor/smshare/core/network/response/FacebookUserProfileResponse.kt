package com.jerryokafor.smshare.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacebookUserProfileResponse(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,
)


@Serializable
data class FacebookUserProfilePictureResponse(
    val data: FacebookUserProfilePicture? = null,
)

@Serializable
data class FacebookUserProfilePicture(
    val width: Int,
    val height: Int,
    val url: String? = null,
    @SerialName("is_silhouette")
    val isSilhouette: Boolean = false,
)


