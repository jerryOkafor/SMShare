package com.jerryokafor.smshare.injection

import AuthManager
import Platform
import org.koin.dsl.module

fun platformModule() = module {
    single { Platform() }
    single { AuthManager() }
}