package com.jerryokafor.core.database

import android.app.Application
import androidx.room.RoomDatabase
import org.koin.dsl.module

fun androidDBModule(app: Application) = module {
    single<RoomDatabase.Builder<AppDatabase>> { createRoomDatabase(app) }
}
