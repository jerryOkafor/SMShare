package com.jerryokafor.smshare.channel

import com.jerryokafor.smshare.core.domain.ChannelConfig
import org.jetbrains.compose.resources.DrawableResource

interface ChannelConfigResource : ChannelConfig{
    val icon:  DrawableResource
}
