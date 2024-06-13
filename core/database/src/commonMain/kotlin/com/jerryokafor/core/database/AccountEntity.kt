package com.jerryokafor.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val avatarUrl: String = "",
    val accessToken: String = "",
    val expiresInt: Int = 0,
    val scope: String = "",
    val created: String
)

