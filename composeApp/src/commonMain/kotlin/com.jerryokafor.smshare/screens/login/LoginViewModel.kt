package screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jerryokafor.core.datastore.UserDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val loginComplete: Boolean = false,
)

class LoginViewModel :
    ViewModel(),
    KoinComponent {
    private val userDataStore: UserDataStore by inject()
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun login(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(20_00)
            userDataStore.loginUser()
            _uiState.update { it.copy(isLoading = false, loginComplete = true) }
        }
    }
}
