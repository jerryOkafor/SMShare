package com.jerryokafor.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File


private fun appDir(): File {
    val os = System.getProperty("os.name").lowercase()
    return when {
        os.contains("win") -> {
            File(System.getenv("AppData"), "smshare/db")
        }

        os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
            File(System.getProperty("user.home"), ".smshare")
        }

        os.contains("mac") -> {
            File(System.getProperty("user.home"), "Library/Application Support/smshare")
        }

        else -> error("Unsupported operating system")
    }
}

fun createAppDatabase(): RoomDatabase.Builder<AppDatabase> {
//        val dbFile = File(System.getProperty("java.io.tmpdir"), DB_NAME)
    val dbFile = File(appDir().also { if (!it.exists()) it.mkdirs() }, DB_NAME)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )

}
