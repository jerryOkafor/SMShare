import channel.ChannelConfig

expect class Platform() {
    val platform: String
}


expect class AuthManager private constructor() {
    var currentChannel: ChannelConfig?
    fun authenticateUser(channelConfig: ChannelConfig)
}

