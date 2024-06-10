package com.jerryokafor.smshare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.jerryokafor.core.database.AppDatabase
import com.jerryokafor.core.datastore.UserData
import com.jerryokafor.core.datastore.UserDataStore
import com.jerryokafor.smshare.channel.ChannelConfig
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class AppViewModel : ViewModel(), KoinComponent {
    private val appDatabase: AppDatabase by inject()
    private val userDataStore: UserDataStore by inject()
    private val supportedChannels: List<ChannelConfig> by inject()

    val userData =
        userDataStore.user.map { AppUiState.Success(it) }
            .onEach { Logger.d("User: $it") }
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

    fun logout() {
        viewModelScope.launch {
            userDataStore.logoOutUser()
        }
    }
}

sealed interface AppUiState {
    data object Loading : AppUiState

    data class Success(val user: UserData) : AppUiState
}
