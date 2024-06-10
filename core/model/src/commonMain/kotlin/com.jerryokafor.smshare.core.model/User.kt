package com.jerryokafor.smshare.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val profilePictureUrl: String? = null,
    val phoneNumber: String? = null,
    val token: String? = null,
)
