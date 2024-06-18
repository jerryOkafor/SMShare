package com.jerryokafor.smshare.core.model

enum class AccountType {
    LINKEDIN,
    TWITTER_X,
    FACEBOOK,
}

data class Account(
    val id:Long,
    val type: AccountType,
    val name: String = "",
    val description: String = "",
    val isSelected: Boolean = false,
    val avatarUrl: String = "",
    val postsCount:Int = 0
)
