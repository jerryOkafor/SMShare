package com.jerryokafor.smshare.screens.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.jerryokafor.core.database.AppDatabase
import com.jerryokafor.core.database.toDomainModel
import com.jerryokafor.smshare.core.model.Account
import com.jerryokafor.smshare.core.model.AccountType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ComposeMessageViewModel : ViewModel(), KoinComponent {
    private val database: AppDatabase by inject()

    private val _uiState = MutableStateFlow(ComposeMessageUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            database.getAccountDao().getAll().map { it.toDomainModel() }.let { accounts ->
                _uiState.update { it.copy(targetAccounts = accounts) }
            }
        }
    }

    fun addNewTargetChannel(type: AccountType) {
        val newList = _uiState.value.targetAccounts.toMutableList().map {
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

    fun bindDefaultAccountId(accountId: Long?) {
        Logger.d("Account Id: $accountId")
        val newList = _uiState.value.targetAccounts.toMutableList().map {
            if (it.id == accountId) {
                it.copy(isSelected = true)
            } else {
                it
            }
        }
        _uiState.update { it.copy(targetAccounts = newList) }
    }
}

data class ComposeMessageUiState(
    val targetAccounts: List<Account> = emptyList(),
)
