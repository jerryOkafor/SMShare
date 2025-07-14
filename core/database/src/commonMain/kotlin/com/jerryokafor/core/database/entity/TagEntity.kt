package com.jerryokafor.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jerryokafor.smshare.core.model.Tag


@Entity(tableName = "tags", indices = [Index(value = ["tag"], unique = true)])
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tag: String,
    val groupId: Long,
    val description: String? = null,
) {
    fun fromDomainEntity(tag: Tag): TagEntity = TagEntity(
        id = tag.id,
        groupId = tag.groupId,
        description = tag.description,
        tag = tag.tag,
    )
}


fun TagEntity.toDomainModel(): Tag = Tag(
    id = id,
    tag = tag,
    groupId = groupId,
    description = description
)
