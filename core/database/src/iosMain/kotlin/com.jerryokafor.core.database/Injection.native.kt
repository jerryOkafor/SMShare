package com.jerryokafor.core.database

import org.koin.dsl.module

fun nativeDBModule() =
    module {
        single<AppDatabase> { createAppDatabase() }
    }
