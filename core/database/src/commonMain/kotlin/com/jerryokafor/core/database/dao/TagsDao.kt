package com.jerryokafor.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jerryokafor.core.database.entity.TagEntity
import com.jerryokafor.core.database.entity.TagGroupAndTagsEntity
import com.jerryokafor.core.database.entity.TagGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTagGroup(group: TagGroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTag(tag: TagEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTags(vararg tag: TagEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTags(tags: List<TagEntity>)

    @Query("SELECT * FROM tags")
    fun allTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tags WHERE groupId = :groupId")
    fun tagByGroupId(groupId: Long): Flow<List<TagEntity>>

    @Delete
    suspend fun deleteTag(tag: TagEntity): Int

    @Query("SELECT * FROM tagGroups")
    fun tagsGroupsAndTags(): Flow<List<TagGroupAndTagsEntity>>

    @Transaction
    suspend fun creatTagGroupAndTags(tagGroupEntity: TagGroupEntity, tags: List<TagEntity>) {
        createTagGroup(tagGroupEntity)
        addTags(tags)
    }

}
