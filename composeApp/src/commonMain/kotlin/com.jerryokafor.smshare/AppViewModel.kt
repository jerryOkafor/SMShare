package com.jerryokafor.smshare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.jerryokafor.core.datastore.UserData
import com.jerryokafor.core.datastore.UserDataStore
import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfig
import com.jerryokafor.smshare.core.config.SMShareConfig
import com.jerryokafor.smshare.core.model.Account
import com.jerryokafor.smshare.core.model.AccountType
import com.jerryokafor.smshare.core.network.util.NetworkMonitor
import com.jerryokafor.smshare.platform.Platform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class AppViewModel : ViewModel(), KoinComponent {
    private val userDataStore: UserDataStore by inject()
    private val supportedChannels: List<ChannelConfig> by inject()
    private val networkMonitor: NetworkMonitor by inject()
    private val platForm: Platform by inject()
    private val channelConfigAuthManager: ChannelAuthManager by inject()

    val userData = userDataStore.user.map { AppUiState.Success(user = it, platform = platForm) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = AppUiState.Loading,
        )

    val channels: StateFlow<List<ChannelConfig>> =
        flowOf(supportedChannels)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList(),
            )
    val isOnLine: StateFlow<Boolean> = networkMonitor.isOnline
        .onEach { Logger.d("isOnLine: $it") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts = _accounts.asStateFlow()

    init {
        _accounts.update {
            listOf(
                Account(name = "Jerry Okafor", type = AccountType.FACEBOOK),
                Account(name = "Jerry Okafor", type = AccountType.LINKEDIN),
                Account(name = "Jerry Okafor", type = AccountType.TWITTER_X),
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            userDataStore.logoOutUser()
        }
    }

    fun authenticateChannel(code: String, state: String?) {
        Logger.d("Fetching Token: $code | $state")
        viewModelScope.launch {
            try {
                val tokenResponse =
                    channelConfigAuthManager.currentChannelConfig?.requestAccessToken(
                        code = code,
                        redirectUrl = SMShareConfig.redirectUrl
                    )

                Logger.d("access Token: $tokenResponse")
            } catch (e: Exception) {
                Logger.w(e.message ?: "Error creating access token")
            }
        }
    }
}

sealed interface AppUiState {
    data object Loading : AppUiState

    data class Success(val user: UserData, val platform: Platform) : AppUiState
}
