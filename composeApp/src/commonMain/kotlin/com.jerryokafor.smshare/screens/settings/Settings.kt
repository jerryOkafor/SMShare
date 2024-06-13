package com.jerryokafor.smshare.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jerryokafor.smshare.SMShareTopAppBarState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun SettingsPreview() {
    MaterialTheme {
        Settings()
    }
}

@Composable
fun Settings(
    onSetupTopAppBar: () -> Unit = {},
    onSetUpBottomAppBar: () -> Unit = {}
) {
    val currentOnSetupTopAppBar by rememberUpdatedState(onSetupTopAppBar)
    val currentOnSetUpBottomAppBar by rememberUpdatedState(onSetUpBottomAppBar)

    LaunchedEffect(true) {
        currentOnSetupTopAppBar()
        currentOnSetUpBottomAppBar()
    }
    Box(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(
                Alignment.Center,
            ),
            text = "Welcome to settings",
        )
    }
}

const val settingRoute = "settings"

fun NavGraphBuilder.settingsScreen(
    onSetupTopAppBar: () -> Unit = {},
    onSetUpBottomAppBar: () -> Unit
) {
    composable(settingRoute) {
        Settings(
            onSetupTopAppBar = onSetupTopAppBar,
            onSetUpBottomAppBar = onSetUpBottomAppBar
        )
    }
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(route = settingRoute, navOptions = navOptions)
}
