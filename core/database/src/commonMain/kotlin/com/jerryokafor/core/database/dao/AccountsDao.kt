package com.jerryokafor.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jerryokafor.core.database.entity.AccountAndUserProfileEntity
import com.jerryokafor.core.database.entity.AccountEntity
import com.jerryokafor.core.database.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAccount(account: AccountEntity)

    //Todo: Move to its own DAO
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserProfile(userProfileEntity: UserProfileEntity)

    @Query("SELECT count(*) FROM accounts")
    suspend fun count(): Int

    @Query("SELECT * FROM accounts")
    fun getAllAsFlow(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts")
    suspend fun getAll(): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE id=:id")
    suspend fun getAccount(id: Long): AccountEntity?

    @Query("DELETE FROM accounts WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE accounts SET isSelected = (id = :id)")
    suspend fun selectById(id: Long)


    @Transaction
    suspend fun insertAccountAndUserProfileEntity(
        account: AccountEntity,
        userProfileEntity: UserProfileEntity
    ) {
        addAccount(account)
        addUserProfile(userProfileEntity)
    }

    @Transaction
    @Query("SELECT * FROM accounts")
    fun getAccountAndUserProfilesAsFlow(): Flow<List<AccountAndUserProfileEntity>>

    @Transaction
    @Query("SELECT * FROM accounts")
    fun getAccountAndUserProfiles(): List<AccountAndUserProfileEntity>
}
