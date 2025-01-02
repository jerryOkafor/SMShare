package com.jerryokafor.core.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val isLoggedIn: Boolean = false,
    val token: String? = null
)
