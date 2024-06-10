package com.jerryokafor.core.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.io.File

fun createAppDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DB_NAME)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
