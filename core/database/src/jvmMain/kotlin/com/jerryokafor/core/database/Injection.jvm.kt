package com.jerryokafor.core.database

import androidx.room.RoomDatabase
import org.koin.dsl.module

fun jvmDBModule() = module {
    single<RoomDatabase.Builder<AppDatabase>> { createAppDatabase() }
}
