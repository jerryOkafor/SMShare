package com.jerryokafor.smshare.injection

import com.jerryokafor.core.database.AppDatabase
import com.jerryokafor.core.database.nativeDBModule
import com.jerryokafor.core.datastore.nativeUserDatastoreModule
import com.jerryokafor.smshare.IOSChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.core.network.util.injection.iosNetworkModules
import com.jerryokafor.smshare.platform.Platform
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

fun iOSModules() = module {
    includes(
        nativeDBModule(),
        nativeUserDatastoreModule(),
        iosNetworkModules()
    )

    single { Platform() }
    single<ChannelAuthManager> { IOSChannelAuthManager() }
}

// Needed for iOS
fun initKoin() = initKoin {
    modules(iOSModules())
}

class IOSInjectionHelper : KoinComponent {
    val appDatabase: AppDatabase by inject()
    val channelAuthManager: ChannelAuthManager by inject()
}
