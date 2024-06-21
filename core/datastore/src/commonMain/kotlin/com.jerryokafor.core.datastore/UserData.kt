package com.jerryokafor.core.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val isLoggedIn: Boolean = false,
)
