package com.jerryokafor.smshare

import android.app.Application
import com.jerryokafor.smshare.injection.androidModules
import com.jerryokafor.smshare.injection.initKoin
import org.koin.android.ext.koin.androidContext

class SMShareApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@SMShareApp)
            modules(androidModules(this@SMShareApp))
        }
    }
}
