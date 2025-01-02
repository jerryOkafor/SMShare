package com.jerryokafor.smshare.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.jerryokafor.smshare.core.domain.Failure
import com.jerryokafor.smshare.core.domain.Success
import com.jerryokafor.smshare.core.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val loginComplete: Boolean = false,
    val toast: String? = null,
    val error: String? = null
)

@OptIn(SavedStateHandleSaveableApi::class)
class LoginViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel(), KoinComponent {
    private val userRepository: UserRepository by inject()

    var userName by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("username2"))
    }
        private set

    var password by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("P@ssw0rd2"))
    }
        private set


    private val _uiState =  MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login() {
        val userNameStr = userName.text
        val passwordStr = password.text

        userRepository.login(userNameStr, passwordStr)
            .onStart {
                _uiState.update { current: LoginUiState -> current.copy(isLoading = true) }
            }
            .onEach {
                when (it) {
                    is Failure -> {
                        _uiState.update { current: LoginUiState ->
                            current.copy(
                                isLoading = false,
                                error = it.errorResponse
                            )
                        }
                    }

                    is Success -> {
                        _uiState.update { current: LoginUiState ->
                            current.copy(
                                isLoading = false,
                                toast = "Login Successful"
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onUserNameChange(userName: TextFieldValue) {
        this.userName = userName
    }

    fun onPasswordChange(password: TextFieldValue) {
        this.password = password
    }
}
