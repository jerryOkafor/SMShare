package com.jerryokafor.core.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory

fun createAppDatabase(): AppDatabase {
    val dbFilePath = NSHomeDirectory() + DB_NAME

    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() },
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
