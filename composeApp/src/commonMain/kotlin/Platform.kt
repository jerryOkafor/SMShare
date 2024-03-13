import channel.ChannelConfig

expect class Platform() {
    val platform: String
}


expect class AuthManager {
    var currentChannel: ChannelConfig?
    fun authenticateUser(channelConfig: ChannelConfig)
}

