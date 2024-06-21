package com.jerryokafor.core.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase

fun createRoomDatabase(app: Application): RoomDatabase.Builder<AppDatabase> {
    val dbFile = app.getDatabasePath(DB_NAME)

    return Room
        .databaseBuilder<AppDatabase>(
            context = app,
            name = dbFile.absolutePath,
        ).enableMultiInstanceInvalidation()
//        .setAutoCloseTimeout()
}
