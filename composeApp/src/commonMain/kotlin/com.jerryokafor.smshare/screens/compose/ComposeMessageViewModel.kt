package com.jerryokafor.smshare.screens.compose

import androidx.lifecycle.ViewModel
import com.jerryokafor.smshare.channel.ChannelConfig
import com.jerryokafor.smshare.core.model.Account
import com.jerryokafor.smshare.core.model.AccountType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ComposeMessageViewModel : ViewModel(), KoinComponent {
    private val supportedChannelConfigs: List<ChannelConfig> by inject()

    private val _uiState = MutableStateFlow(
        ComposeMessageUiState(
            targetAccounts = supportedChannelConfigs.map {
                Account(
                    isSelected = false,
                    type = it.accountType,
                    name = it.name,
                    description = it.description,
                )
            },
        ),
    )
    val uiState = _uiState.asStateFlow()

    fun addNewTargetChannel(type: AccountType) {
        val newList =
            _uiState.value.targetAccounts.toMutableList().map {
                if (it.type == type) {
                    it.copy(isSelected = true)
                } else {
                    it
                }
            }
        _uiState.update { it.copy(targetAccounts = newList) }
    }

    fun removeTargetChannel(type: AccountType) {
        val newList = _uiState.value.targetAccounts.toMutableList().map {
            if (it.type == type) {
                it.copy(isSelected = false)
            } else {
                it
            }
        }
        _uiState.update { it.copy(targetAccounts = newList) }
    }
}

data class ComposeMessageUiState(
//    val supportedChannels: List<Channel> = emptyList(),
    val targetAccounts: List<Account> = emptyList(),
)
