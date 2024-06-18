package com.jerryokafor.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerryokafor.smshare.core.model.Account
import com.jerryokafor.smshare.core.model.AccountType

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

fun AccountEntity.toDomainModel() = Account(
    id = this.id,
    type = AccountType.TWITTER_X,
    name = this.name,
    description = this.description,
    avatarUrl = this.avatarUrl,
    postsCount = 0
)

