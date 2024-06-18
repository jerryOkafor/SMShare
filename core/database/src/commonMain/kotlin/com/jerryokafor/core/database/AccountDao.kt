package com.jerryokafor.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert
    suspend fun insert(item: AccountEntity)

    @Query("SELECT count(*) FROM accounts")
    suspend fun count(): Int

    @Query("SELECT * FROM accounts")
    fun getAllAsFlow(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts")
    suspend fun getAll(): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE id=:id")
    suspend fun getAccount(id: Long): AccountEntity?
}
