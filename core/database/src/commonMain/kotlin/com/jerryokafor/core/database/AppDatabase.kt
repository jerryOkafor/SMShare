package com.jerryokafor.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.migration.Migration
import com.jerryokafor.core.database.dao.AccountDao
import com.jerryokafor.core.database.entity.AccountEntity
import com.jerryokafor.core.database.entity.UserProfileEntity

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(entities = [AccountEntity::class, UserProfileEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountDao
}

val MIGRATIONS = arrayOf<Migration>()

fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase = builder
    .addMigrations(*MIGRATIONS)
    .fallbackToDestructiveMigrationOnDowngrade(true)
    .build()
