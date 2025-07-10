package com.jerryokafor.core.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.jerryokafor.smshare.core.model.AccountAndProfile

data class AccountAndUserProfileEntity(
    @Embedded val account: AccountEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val profile: UserProfileEntity
)


fun AccountAndUserProfileEntity.toDomainModel() = AccountAndProfile(
    account = this.account.toDomainModel(),
    profile = this.profile.toDomainModel()
)
