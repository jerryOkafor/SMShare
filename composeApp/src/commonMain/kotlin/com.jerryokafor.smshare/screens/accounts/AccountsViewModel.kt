package com.jerryokafor.smshare.screens.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryokafor.core.database.AppDatabase
import com.jerryokafor.core.database.entity.toDomainModel
import com.jerryokafor.smshare.core.domain.ChannelConfig
import com.jerryokafor.smshare.core.domain.AccountRepository
import com.jerryokafor.smshare.core.model.Account
import com.jerryokafor.smshare.core.network.util.NetworkMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

data class ManageAccountUIState(
    val errorMessage: String = "",
)

class AccountsViewModel :
    ViewModel(),
    KoinComponent {

    private val networkMonitor: NetworkMonitor by inject()
    private val database: AppDatabase by inject()
    private val accountRepository: AccountRepository by inject()
    private val supportedChannels: List<ChannelConfig> by inject()

    val accounts = database.getAccountDao().getAllAsFlow()
        .map { entities -> entities.map { it.toDomainModel() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState = MutableStateFlow(ManageAccountUIState())
    val uiState = _uiState.asStateFlow()


    fun onAccountAction(account: Account, action: AccountAction) {
        when (action) {
            AccountAction.Refresh -> viewModelScope.launch {
                // Refresh Account access token
                //Download new profile pictures and other account details

                val currentChannelConfig =
                    supportedChannels.firstOrNull { it.accountType == account.type }

                println("Current ChannelConfig: $currentChannelConfig \n access token: ${account.accessToken}")

                val isOnLine = networkMonitor.isOnline.firstOrNull() ?: false

                if (!isOnLine) {
                    _uiState.update { it.copy(errorMessage = "No internet connection, please check your internet connection.") }
                    return@launch
                }

                try {
                    val userProfile = currentChannelConfig?.userProfile(account.accessToken)
                    print("User profile: $userProfile")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            AccountAction.Remove -> viewModelScope.launch {
                accountRepository.removeAccount(account)
            }

            AccountAction.Settings -> {}
        }
    }

    fun cleaErrorMessage() {
        _uiState.update { it.copy(errorMessage = "") }
    }

}
