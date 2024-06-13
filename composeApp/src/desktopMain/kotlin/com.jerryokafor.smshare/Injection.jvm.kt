package com.jerryokafor.smshare

import com.jerryokafor.core.database.jvmDBModule
import com.jerryokafor.core.datastore.jvmDataStoreModule
import com.jerryokafor.smshare.DesktopChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.core.network.injection.jvmNetworkModule
import com.jerryokafor.smshare.platform.Platform
import org.koin.dsl.module

fun desktopModule() = module {
    includes(jvmDataStoreModule(), jvmDBModule(), jvmNetworkModule())
    single<ChannelAuthManager> { DesktopChannelAuthManager() }

    single { Platform() }
}
