package com.jerryokafor.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

fun createAppDatabase(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/$DB_NAME"
    return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
}
