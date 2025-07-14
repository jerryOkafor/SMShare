package com.jerryokafor.smshare.screens.manageTags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryokafor.core.database.tagGroups
import com.jerryokafor.smshare.core.domain.TagRepository
import com.jerryokafor.smshare.core.model.Tag
import com.jerryokafor.smshare.core.model.TagGroup
import com.jerryokafor.smshare.core.model.TagGroupsAndTags
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

data class ManageTagsUIState(val localTags: List<TagGroupsAndTags> = emptyList())

class ManageTagsViewModel : ViewModel(), KoinComponent {
    private val tagRepository: TagRepository by inject()

    val tagsAndGroups: StateFlow<List<TagGroupsAndTags>> = flowOf(
        tagGroups
            .mapIndexed { index, (groupName, tags) ->
                val groupId = index.toLong() + 1
                TagGroupsAndTags(
                    group = TagGroup(id = groupId, name = groupName),
                    tags = tags.mapIndexed { index, tag ->
                        val tagId = groupId + (index.toLong() + 1)
                        Tag(tagId, tag = tag, groupId = groupId, description = "$tag tags")
                    })
            }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    val uiState = tagRepository.getTagGroupsAndTagsAsFlow()
        .map { ManageTagsUIState(localTags = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = ManageTagsUIState()
        )
}
