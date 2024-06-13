package com.jerryokafor.smshare.screens.analytics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jerryokafor.smshare.SMShareTopAppBarState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.main_nav_title_analytics

@Preview
@Composable
private fun AnalyticsPreview() {
    MaterialTheme {
        Analytics()
    }
}

@Composable
fun Analytics(
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
            modifier =
            Modifier.align(
                Alignment.Center,
            ),
            text = "Welcome to analytics",
        )
    }
}

val analyticsRoute = "analytics"

fun NavGraphBuilder.analyticsScreen(
    onSetupTopAppBar: () -> Unit = {},
    onSetUpBottomAppBar: () -> Unit
) {
    composable(route = analyticsRoute) {
        Analytics(
            onSetupTopAppBar = onSetupTopAppBar,
            onSetUpBottomAppBar = onSetUpBottomAppBar
        )
    }
}

fun NavController.navigateToAnalytics(navOptions: NavOptions? = null) {
    navigate(route = analyticsRoute, navOptions = navOptions)
}
