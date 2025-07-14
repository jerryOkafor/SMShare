package com.jerryokafor.core.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.jerryokafor.smshare.core.model.TagGroupsAndTags

data class TagGroupAndTagsEntity(
    @Embedded
    val group: TagGroupEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "groupId",
    )
    val tags: List<TagEntity>,
)

fun TagGroupAndTagsEntity.toDomainModel(): TagGroupsAndTags = TagGroupsAndTags(
    group = group.toDomainModel(),
    tags = tags.map { it.toDomainModel() }
)
