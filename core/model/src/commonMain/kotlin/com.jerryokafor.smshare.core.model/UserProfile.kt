package com.jerryokafor.smshare.core.model


data class ProfileLocal(
    val country: String? = null,
    val language: String? = null
)

data class UserProfile(
    val subjectId: String,
    val name: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val picture: String? = null,
    val email: String? = null,
    val locale: ProfileLocal? = null,
)


