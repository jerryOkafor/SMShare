package com.jerryokafor.smshare.core.domain

import com.apollographql.apollo.ApolloClient
import com.jerryokafor.core.database.dao.TagsDao
import com.jerryokafor.core.database.entity.toDomainModel
import com.jerryokafor.smshare.core.model.TagGroupsAndTags
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TagRepository {
    fun getTagGroupsAndTagsAsFlow(): Flow<List<TagGroupsAndTags>>
}

class DefaultTagRepository(
    private val tagDao: TagsDao,
    private val apolloClient: ApolloClient,
    private val ioDispatcher: CoroutineDispatcher,
) : TagRepository {
    override fun getTagGroupsAndTagsAsFlow(): Flow<List<TagGroupsAndTags>> =
        tagDao.tagsGroupsAndTags()
            .map { tagGroupsAndTags -> tagGroupsAndTags.map { it.toDomainModel() } }


}
