package com.jerryokafor.smshare.core.model

enum class AccountType {
    LINKEDIN,
    TWITTER_X,
    FACEBOOK,

    UNKNOWN,
}

data class Account(
    val id: Long,
    val type: AccountType,
    val name: String = "",
    val description: String = "",
    val isSelected: Boolean = false,
    val isSelectedForCompose: Boolean = false,
    val postsCount: Int = 0,

    // Todo : Decide if we should keep this prop here
    val accessToken: String = "",
)

data class AccountAndProfile(
    val account: Account,
    val profile: UserProfile
)
