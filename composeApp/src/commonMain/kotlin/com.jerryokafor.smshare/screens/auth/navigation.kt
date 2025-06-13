@file:Suppress("ktlint:standard:filename", "InvalidPackageDeclaration", "MatchingDeclarationName")

package com.jerryokafor.smshare.screens.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.jerryokafor.smshare.SMShareBottomAppBarState
import com.jerryokafor.smshare.SMShareTopAppBarState
import com.jerryokafor.smshare.screens.auth.createAccount.CreateAccountScreen
import com.jerryokafor.smshare.screens.auth.login.LoginScreen
import kotlinx.serialization.Serializable

@Suppress("MatchingDeclarationName")
data object AuthDestinations {
    @Serializable
    data object SignIn

    @Serializable
    data object SignUp
}

@Suppress("LongParameterList")
inline fun <reified T : Any> NavGraphBuilder.installAuth(
    noinline onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit = {},
    noinline onSetUpBottomAppBar: (SMShareBottomAppBarState?) -> Unit = {},
    noinline onCreateAccountClick: () -> Unit = {},
    noinline onLoginComplete: () -> Unit = {},
    noinline onLoginClick: () -> Unit = {},
    noinline onSignUpComplete: () -> Unit = {},
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

        composable<AuthDestinations.SignUp> {
            CreateAccountScreen(
                onSetupTopAppBar = onSetupTopAppBar,
                onSetUpBottomAppBar = onSetUpBottomAppBar,
                onLoginClick = onLoginClick,
                onSignUpComplete = onSignUpComplete,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    navigate(route = AuthDestinations.SignIn, navOptions = navOptions)
}

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    navigate(route = AuthDestinations.SignUp, navOptions = navOptions)
}
