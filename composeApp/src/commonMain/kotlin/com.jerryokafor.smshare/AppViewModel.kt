package com.jerryokafor.smshare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.jerryokafor.core.database.AppDatabase
import com.jerryokafor.core.database.entity.toDomainModel
import com.jerryokafor.core.datastore.model.UserData
import com.jerryokafor.core.datastore.store.UserDataStore
import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.channel.ChannelConfigResource
import com.jerryokafor.smshare.core.domain.ChannelConfig
import com.jerryokafor.smshare.core.domain.AccountRepository
import com.jerryokafor.smshare.core.model.AccountAndProfile
import com.jerryokafor.smshare.core.network.util.NetworkMonitor
import com.jerryokafor.smshare.navigation.Auth
import com.jerryokafor.smshare.platform.Platform
import com.jerryokafor.smshare.screens.posts.Posts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class AppViewModel :
    ViewModel(),
    KoinComponent {
    private val database: AppDatabase by inject()
    private val accountRepository: AccountRepository by inject()
    private val userDataStore: UserDataStore by inject()
    private val supportedChannels: List<ChannelConfigResource> by inject()
    private val networkMonitor: NetworkMonitor by inject()
    private val platForm: Platform by inject()
    private val channelConfigAuthManager: ChannelAuthManager by inject()

    private val _startDestination = MutableStateFlow<Any?>(null)
    val startDestination = _startDestination.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    val userData = userDataStore.user
        .map { AppUiState.Success(user = it, platform = platForm) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = AppUiState.Loading,
        )

    val isOnLine: StateFlow<Boolean> = networkMonitor.isOnline
        .onEach { Logger.d("isOnLine: $it") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    private val _currentAccount = MutableStateFlow<AccountAndProfile?>(null)
    val currentAccount: StateFlow<AccountAndProfile?> = _currentAccount.asStateFlow()

    val accountsAndProfile = database
        .getAccountDao()
        .getAccountAndUserProfilesAsFlow()
        .map {
            it.mapIndexed { index, (entity, profile) ->
                AccountAndProfile(
                    account = entity.toDomainModel().copy(isSelected = index == 0),
                    profile = profile.toDomainModel()
                )
            }
        }.onEach { accountAndProfile ->
            if (currentAccount.isNull()) {
                _currentAccount.update { accountAndProfile.firstOrNull() }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )

    val channels: StateFlow<List<ChannelConfigResource>> = accountsAndProfile
        .map { accountsAndProfileList ->
            supportedChannels.filter { config ->
                accountsAndProfileList.none { accountsAndProfile ->
                    accountsAndProfile.account.type == config.accountType
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )

    init {
        viewModelScope.launch {
            userDataStore.user
                .takeWhile { startDestination.value == null }
                .collectLatest { userData ->
                    val startDestination: Any = if (userData.isLoggedIn) Posts else Auth
                    _startDestination.update { startDestination }
                }
        }

//        viewModelScope.launch {
//            try {
//                Logger.withTag("Testing").d("Starting KRPC call")
//
//                @Suppress("MagicNumber")
//                val userService = HttpClient { installKrpc() }
//                    .rpc {
//                        url {
//                            host = DEV_SERVER_HOST
//                            port = 8080
//                            encodedPath = "/api"
//                        }
//
//                        rpcConfig {
//                            serialization {
//                                json()
//                            }
//                        }
//                    }.withService<RPCUserService>()
//
//                Logger.withTag("Testing").d("KRPC client service: $userService")
//
//                val helloResponse = userService.hello(message = "Welcome message")
//                Logger.withTag("Testing").d("Hello: $helloResponse")
//
//                userService.subscribeToNews().collect { news ->
//                    Logger.withTag("Testing").d("Testing News: $news")
//                }
//            } catch (e: Throwable) {
//                Logger.withTag("Testing").e("Error: ${e.message}", e)
//            }
//        }
    }

    fun logout() {
        viewModelScope.launch {
            userDataStore.logoOutUser()
        }
    }

    fun authoriseChannel(channel: ChannelConfig) {
        viewModelScope.launch {
            _isLoading.update { true }
            channelConfigAuthManager.authenticateUser(channel)
        }
    }

    fun exchangeCodeForAccessToken(
        code: String,
        state: String?,
    ) {
        println("Fetching Access token: Code : $code \nState: $state")
        viewModelScope.launch {
            try {
                val channelConfig = channelConfigAuthManager.channelConfig!!
                val tokenResponse = channelConfig.exchangeCodeForAccessToken(
                    code = code,
                    redirectUrl = channelConfigAuthManager.getRedirectUrl(),
                    challenge = channelConfigAuthManager.codeVerifier,
                )
                val userProfile =
                    channelConfig.userProfile(accessToken = tokenResponse.accessToken!!)
                accountRepository.addAccount(
                    channelConfig = channelConfig,
                    tokenResponse = tokenResponse,
                    userProfile = userProfile
                ) 
                
                _isLoading.update { false }

            } catch (e: Exception) {
                println("Error: ${e.message}")
                _isLoading.update { false }
            }
        }
    }
}

sealed interface AppUiState {
    data object Loading : AppUiState

    data class Success(
        val user: UserData,
        val platform: Platform,
    ) : AppUiState
}

fun <T> StateFlow<T?>.isNull(): Boolean = this.value == null
