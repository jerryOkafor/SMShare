package com.jerryokafor.smshare.screens.drafts

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
import smshare.composeapp.generated.resources.main_nav_title_drafts

@Preview
@Composable
private fun DraftsPreview() {
    MaterialTheme {
        Drafts()
    }
}

@Composable
fun Drafts(
    onSetupTopAppBar: (SMShareTopAppBarState) -> Unit = {},
    onSetUpBottomAppBar: () -> Unit = {},
    onMoreMenuClick: () -> Unit = {},
) {
    val title = stringResource(Res.string.main_nav_title_drafts)
    val currentOnSetupTopAppBar by rememberUpdatedState(onSetupTopAppBar)
    val currentOnSetUpBottomAppBar by rememberUpdatedState(onSetUpBottomAppBar)

    LaunchedEffect(true) {
        currentOnSetupTopAppBar(
            SMShareTopAppBarState(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                actions = {
                    IconButton(onClick = onMoreMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "More",
                        )
                    }
                },
            ),
        )

        currentOnSetUpBottomAppBar()
    }
    Box(Modifier.fillMaxSize()) {
        Text(
            modifier =
                Modifier.align(
                    Alignment.Center,
                ),
            text = "Welcome to drafts",
        )
    }
}

val draftsRoute = "drafts"

fun NavGraphBuilder.draftsScreen(
    onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit = {},
    onSetUpBottomAppBar: () -> Unit,
    onMoreMenuClick: () -> Unit,
) {
    composable(route = draftsRoute) {
        Drafts(
            onSetupTopAppBar = onSetupTopAppBar,
            onSetUpBottomAppBar = onSetUpBottomAppBar,
            onMoreMenuClick = onMoreMenuClick,
        )
    }
}

fun NavController.navigateToDrafts(navOptions: NavOptions? = null) {
    navigate(route = draftsRoute, navOptions = navOptions)
}
