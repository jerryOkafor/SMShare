package com.jerryokafor.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerryokafor.smshare.core.model.TagGroup

@Entity(tableName = "tagGroups")
data class TagGroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String? = null,
) {
    companion object {
        fun fromDomainEntity(tagGroup: TagGroup): TagGroupEntity = TagGroupEntity(
            id = tagGroup.id,
            name = tagGroup.name,
        )
    }
}


fun TagGroupEntity.toDomainModel(): TagGroup = TagGroup(
    id = this.id,
    name = this.name,
)
