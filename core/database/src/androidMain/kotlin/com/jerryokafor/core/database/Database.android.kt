package com.jerryokafor.core.database

import android.app.Application
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun createRoomDatabase(app: Application): AppDatabase {
    val appContext = app.applicationContext
    val dbFile = appContext.getDatabasePath(DB_NAME)

    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
