package com.jerryokafor.smshare.screens.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jerryokafor.smshare.component.AnalyticsItem
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

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
    onSetUpBottomAppBar: () -> Unit = {},
) {
    val currentOnSetupTopAppBar by rememberUpdatedState(onSetupTopAppBar)
    val currentOnSetUpBottomAppBar by rememberUpdatedState(onSetUpBottomAppBar)

    LaunchedEffect(true) {
        currentOnSetupTopAppBar()
        currentOnSetUpBottomAppBar()
    }
    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize().padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(15) {
                AnalyticsItem()
            }
        }
    }
}

@Serializable
data object Analytics

fun NavGraphBuilder.analyticsScreen(
    onSetupTopAppBar: () -> Unit = {},
    onSetUpBottomAppBar: () -> Unit,
) {
    composable<Analytics> {
        Analytics(
            onSetupTopAppBar = onSetupTopAppBar,
            onSetUpBottomAppBar = onSetUpBottomAppBar,
        )
    }
}

fun NavController.navigateToAnalytics(navOptions: NavOptions? = null) {
    navigate(route = Analytics, navOptions = navOptions)
}
