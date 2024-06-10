package com.jerryokafor.smshare.injection

import com.jerryokafor.core.database.jvmDBModule
import com.jerryokafor.core.datastore.jvmDataStoreModule
import com.jerryokafor.smshare.DesktopChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelAuthManager
import org.koin.dsl.module

fun desktopModule() =
    module {
        includes(jvmDataStoreModule(), jvmDBModule())
        single<ChannelAuthManager> { DesktopChannelAuthManager() }
    }
