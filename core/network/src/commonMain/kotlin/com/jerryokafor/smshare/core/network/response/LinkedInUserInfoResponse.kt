package com.jerryokafor.smshare.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkedInUserInfoLocal(
    val country: String? = null,
    val language: String? = null
)

@Serializable
data class LinkedInUserInfoResponse(
    @SerialName("sub")
    val subjectId: String? = null,
    val name: String? = null,
    @SerialName("given_name")
    val givenName: String? = null,
    @SerialName("family_name")
    val familyName: String? = null,
    val picture: String? = null,
    val email: String? = null,
    val locale: LinkedInUserInfoLocal? = null,
)
