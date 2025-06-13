package com.jerryokafor.smshare.screens.auth.createAccount

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.jerryokafor.core.datastore.store.UserDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class CreateAccountUIState(
    val isLoading: Boolean = false,
    val createAccountComplete: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

@OptIn(SavedStateHandleSaveableApi::class)
class CreateAccountViewModel(savedStateHandle: SavedStateHandle) : ViewModel(), KoinComponent {
    private val userDataStore: UserDataStore by inject()

    var email by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("smsshareUser1@yopmail.com"))
    }
        private set

    var password by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("P@ssw0rd2"))
    }
        private set

    private val _uiState = MutableStateFlow(CreateAccountUIState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: TextFieldValue) {
        this.email = email
    }

    fun onPasswordChange(password: TextFieldValue) {
        this.password = password
    }

    fun createAccount() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(3_000)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun handleSuccessMessage(){
        _uiState.update { it.copy(successMessage = null) }
    }
    fun handleErrorMessage(){
        _uiState.update { it.copy(errorMessage = null) }
    }
}
