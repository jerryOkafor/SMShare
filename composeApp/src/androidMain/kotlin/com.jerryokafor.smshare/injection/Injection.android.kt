package com.jerryokafor.smshare.injection

import android.app.Application
import com.jerryokafor.core.database.androidDBModule
import com.jerryokafor.core.datastore.androidDataStoreModule
import com.jerryokafor.smshare.AndroidChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.core.network.injection.commonAndroidNetworkModules
import com.jerryokafor.smshare.platform.Platform
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun androidModules(app: Application) = module {
    includes(
        androidDataStoreModule(app),
        androidDBModule(app),
        commonAndroidNetworkModules(app),
    )

    single { Platform() }
    single<ChannelAuthManager> { AndroidChannelAuthManager(androidContext()) }
}
