@file:Suppress("InvalidPackageDeclaration")

package com.jerryokafor.core.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val enabled: Boolean = false,
)
