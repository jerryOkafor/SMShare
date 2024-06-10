package com.jerryokafor.smshare.core.model

enum class ChannelType {
    LINKEDIN,
    TWITTER_X,
    FACEBOOK,
}

data class Channel(
    val type: ChannelType,
    val name: String = "",
    val description: String = "",
    val isSelected: Boolean = false,
    val avatarUrlImage: String = "",
)
