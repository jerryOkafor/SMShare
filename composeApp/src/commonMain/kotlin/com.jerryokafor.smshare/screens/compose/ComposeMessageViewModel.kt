package com.jerryokafor.smshare.screens.compose

import androidx.lifecycle.ViewModel
import com.jerryokafor.smshare.channel.ChannelConfig
import com.jerryokafor.smshare.core.model.Channel
import com.jerryokafor.smshare.core.model.ChannelType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ComposeMessageViewModel : ViewModel(), KoinComponent {
    private val supportedChannelConfigs: List<ChannelConfig> by inject()

    private val _uiState =
        MutableStateFlow(
            ComposeMessageUiState(
                targetChannels =
                    supportedChannelConfigs.map {
                        Channel(
                            isSelected = false,
                            type = it.channelType,
                            name = it.name,
                            description = it.description,
                        )
                    },
            ),
        )
    val uiState = _uiState.asStateFlow()

    fun addNewTargetChannel(type: ChannelType) {
        val newList =
            _uiState.value.targetChannels.toMutableList().map {
                if (it.type == type) {
                    it.copy(isSelected = true)
                } else {
                    it
                }
            }
        _uiState.update { it.copy(targetChannels = newList) }
    }

    fun removeTargetChannel(type: ChannelType) {
        val newList =
            _uiState.value.targetChannels.toMutableList().map {
                if (it.type == type) {
                    it.copy(isSelected = false)
                } else {
                    it
                }
            }
        _uiState.update { it.copy(targetChannels = newList) }
    }
}

data class ComposeMessageUiState(
//    val supportedChannels: List<Channel> = emptyList(),
    val targetChannels: List<Channel> = emptyList(),
)
