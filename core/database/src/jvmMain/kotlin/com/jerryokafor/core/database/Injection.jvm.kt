package com.jerryokafor.core.database

import org.koin.dsl.module

fun jvmDBModule() =
    module {
        single<AppDatabase> { createAppDatabase() }
    }
