package com.jerryokafor.smshare.core.model

data class Tag(
    val id: Long = 0,
    val tag: String,
    val groupId: Long,
    val description: String? = null,
)


data class TagGroup(
    val id: Long = 0,
    val name: String,
    val description: String? = null,
)

data class TagGroupsAndTags(
    val group: TagGroup,
    val tags: List<Tag>,
)
