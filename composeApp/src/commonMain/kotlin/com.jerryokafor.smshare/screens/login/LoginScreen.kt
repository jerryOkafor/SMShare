package com.jerryokafor.smshare.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jerryokafor.smshare.SMShareBottomAppBarState
import com.jerryokafor.smshare.SMShareTopAppBarState
import com.jerryokafor.smshare.component.SMSShareButton
import com.jerryokafor.smshare.component.SMSShareTextButton
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.compose_multiplatform

@Preview
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}

@Composable
fun LoginScreen(
    onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit = {},
    onSetUpBottomAppBar: (SMShareBottomAppBarState?) -> Unit = {},
    onCreateAccountClick: () -> Unit = {},
    onLoginComplete: () -> Unit = {},
    onShowSnackbar: suspend (String, String?, Boolean) -> Boolean = { _, _, _ -> false },
) {
    val viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val currentOnSetupTopAppBar by rememberUpdatedState(onSetupTopAppBar)
    val currentOnSetUpBottomAppBar by rememberUpdatedState(onSetUpBottomAppBar)
    val currentOnLoginComplete by rememberUpdatedState(onLoginComplete)
    val currentOnShowSnackbar by rememberUpdatedState(onShowSnackbar)

    LaunchedEffect(true) {
        currentOnSetupTopAppBar(null)
        currentOnSetUpBottomAppBar(null)
    }

    LaunchedEffect(viewModel, uiState) {
        launch {
            snapshotFlow { uiState.loginComplete }
                .filter { it }
                .collect { isLoggedIn ->
                    if (isLoggedIn) {
                        currentOnLoginComplete()
                    }
                }
        }
        launch {
            snapshotFlow { uiState.error }
                .filter { it != null }
                .collect { error ->
                    currentOnShowSnackbar(error!!, "", false)
                }
        }

        launch {
            snapshotFlow { uiState.toast }
                .filter { it != null }
                .collect { error ->
                    currentOnShowSnackbar(error!!, "", true)
                }
        }
    }

    val onLoginClick: () -> Unit = {
        viewModel.login()
    }


    Column(
        modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Welcome",
        )
        Spacer(modifier = Modifier.height(40.dp))
        Column {
            Text("Enter email")
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.userName,
                onValueChange = { viewModel.onUserNameChange(it) },
                placeholder = { Text("Enter email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        Column {
            Text("Enter password")
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = { Text("Enter password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    val image =
                        if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        }

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        SMSShareButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onLoginClick,
            isLoading = uiState.isLoading,
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        SMSShareTextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onCreateAccountClick,
        ) {
            Text("Create account")
        }
    }
}


data object AuthDestinations {
    @Serializable
    data object SignIn

    @Serializable
    data object SignUp

}

inline fun <reified T : Any> NavGraphBuilder.signInScreen(
    noinline onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit = {},
    noinline onSetUpBottomAppBar: (SMShareBottomAppBarState?) -> Unit = {},
    noinline onCreateAccountClick: () -> Unit = {},
    noinline onLoginComplete: () -> Unit = {},
    noinline onShowSnackbar: suspend (String, String?, Boolean) -> Boolean = { _, _, _ -> false },
) {
    navigation<T>(startDestination = AuthDestinations.SignIn) {
        composable<AuthDestinations.SignIn> {
            LoginScreen(
                onSetupTopAppBar = onSetupTopAppBar,
                onSetUpBottomAppBar = onSetUpBottomAppBar,
                onCreateAccountClick = onCreateAccountClick,
                onLoginComplete = onLoginComplete,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    navigate(route = AuthDestinations.SignIn, navOptions = navOptions)
}
