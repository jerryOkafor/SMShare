package com.jerryokafor.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerryokafor.smshare.core.model.Account
import com.jerryokafor.smshare.core.model.AccountType

enum class AccountTypeEntity {
    LINKEDIN,
    TWITTER_X,
    FACEBOOK,

    UNKNOWN,

    ;

    companion object {
        fun fromDomainModel(type: AccountType?) = when (type) {
            AccountType.LINKEDIN -> LINKEDIN
            AccountType.TWITTER_X -> TWITTER_X
            AccountType.FACEBOOK -> FACEBOOK
            else -> UNKNOWN
        }
    }
}

@Entity("accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val type: AccountTypeEntity = AccountTypeEntity.UNKNOWN,
    val description: String = "",
    val avatarUrl: String = "",
    val accessToken: String = "",
    val expiresInt: Int = 0,
    val scope: String = "",
    val created: String,
)

fun AccountEntity.toDomainModel() = Account(
    id = id,
    type = type.toDomainModel(),
    name = name,
    description = description,
    avatarUrl = avatarUrl,
    postsCount = 0,
)

fun AccountTypeEntity.toDomainModel() = when (this) {
    AccountTypeEntity.LINKEDIN -> AccountType.LINKEDIN
    AccountTypeEntity.TWITTER_X -> AccountType.TWITTER_X
    AccountTypeEntity.FACEBOOK -> AccountType.FACEBOOK
    AccountTypeEntity.UNKNOWN -> AccountType.UNKNOWN
}
