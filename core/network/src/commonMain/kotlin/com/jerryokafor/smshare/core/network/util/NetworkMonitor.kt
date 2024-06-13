package com.jerryokafor.smshare.core.network.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}

class AlwaysOnlineNetworkMonitor : NetworkMonitor {
    override val isOnline: Flow<Boolean> = flowOf(true)

}