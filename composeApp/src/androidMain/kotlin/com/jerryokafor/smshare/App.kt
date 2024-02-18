package com.jerryokafor.smshare

import android.app.Application
import com.jerryokafor.smshare.injection.platformModule
import injection.initKoin
import org.koin.android.ext.koin.androidContext

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@App)
            modules(platformModule())
        }
    }
}