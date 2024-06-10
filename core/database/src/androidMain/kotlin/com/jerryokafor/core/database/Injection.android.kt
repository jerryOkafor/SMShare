package com.jerryokafor.core.database

import android.app.Application
import org.koin.dsl.module

fun androidDBModule(app: Application) =
    module {
        single<AppDatabase> { createRoomDatabase(app) }
    }
