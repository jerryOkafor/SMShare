package com.jerryokafor.core.database.entity

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

    /**
     * Primary ID for the given account from the provider
     * */
    val subjectId: String,
    val name: String = "",
    val type: AccountTypeEntity = AccountTypeEntity.UNKNOWN,
    val description: String = "",
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
    postsCount = 0,
    accessToken = accessToken,
)

fun AccountTypeEntity.toDomainModel() = when (this) {
    AccountTypeEntity.LINKEDIN -> AccountType.LINKEDIN
    AccountTypeEntity.TWITTER_X -> AccountType.TWITTER_X
    AccountTypeEntity.FACEBOOK -> AccountType.FACEBOOK
    AccountTypeEntity.UNKNOWN -> AccountType.UNKNOWN
}
