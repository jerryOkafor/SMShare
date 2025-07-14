/*
 * MIT License
 *
 * Copyright (c) 2024 SM Share Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jerryokafor.smshare.screens.manageAccounts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.jerryokafor.smshare.SMShareBottomAppBarState
import com.jerryokafor.smshare.SMShareTopAppBarState
import com.jerryokafor.smshare.component.ChannelWithName
import com.jerryokafor.smshare.component.iconIndicatorForAccountType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.avatar6

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit = {},
    onSetUpBottomAppBar: (SMShareBottomAppBarState?) -> Unit = {},
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?, Boolean) -> Boolean = { _, _, _ -> false },
) {
    val viewModel: AccountsViewModel = koinViewModel()
    val accountAndProfiles by viewModel.accountAndProfiles.collectAsState(initial = emptyList())

    val currentOnSetupTopAppBar by rememberUpdatedState(onSetupTopAppBar)
    val currentOnSetUpBottomAppBar by rememberUpdatedState(onSetUpBottomAppBar)

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        currentOnSetupTopAppBar(
            SMShareTopAppBarState {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Add new account",
                            )
                        }
                    },
                    title = { Text("Manage Accounts") },
                    actions = {},
                )
            },
        )

        currentOnSetUpBottomAppBar(null)
    }

    LaunchedEffect(viewModel) {
        launch {
            snapshotFlow { uiState.errorMessage }
                .filter { it.isNotBlank() }
                .collectLatest { errorMessage ->
                    onShowSnackbar(errorMessage, "Ok", false)
                    viewModel.cleaErrorMessage()
                }
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(accountAndProfiles) { (account,profile) ->
            Surface(
                color = Color.Transparent,
                modifier = Modifier.fillMaxWidth(), onClick = { }) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalPlatformContext.current)
                                .data(profile.picture)
                                .error { it.placeholder() }
                                .build(),
                            placeholder = painterResource(Res.drawable.avatar6),
                            contentScale = ContentScale.Crop,
                        )
                        
                        ChannelWithName(
                            name = account.name,
                            avatarSize = 38.dp,
                            avatar = painter,
                            indicator = iconIndicatorForAccountType(account.type)
                        )

                        Box {
                            var expanded by remember { mutableStateOf(false) }
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "More options")
                                DropdownMenuWithDetails(
                                    modifier = Modifier,
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    onAction = {
                                        viewModel.onAccountAction(account, it)
                                    }
                                )
                            }


                        }
                    }
                    Spacer(Modifier.height(4.dp))

                    HorizontalDivider(
                        modifier = Modifier
                            .padding(start = 70.dp, end = 16.dp),
                        thickness = (0.5).dp,
                        color = DividerDefaults.color.copy(alpha = 0.5f),
                    )
                }
            }
        }
    }


}

const val manageAccounts = "manageAccounts"

inline fun NavGraphBuilder.installAccountsScreen(
    noinline onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit = {},
    noinline onSetUpBottomAppBar: (SMShareBottomAppBarState?) -> Unit = {},
    noinline onBackClick: () -> Unit = {},
    noinline onShowSnackbar: suspend (String, String?, Boolean) -> Boolean = { _, _, _ -> false },
) {
    composable(route = manageAccounts) {
        AccountsScreen(
            onSetupTopAppBar = onSetupTopAppBar,
            onSetUpBottomAppBar = onSetUpBottomAppBar,
            onBackClick = onBackClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}

fun NavController.navigateToManageAccounts(navOptions: NavOptions? = null) {
    navigate(route = manageAccounts, navOptions = navOptions)
}


sealed interface AccountAction {
    data object Refresh : AccountAction
    data object Remove : AccountAction
    data object Settings : AccountAction
}

@Composable
fun DropdownMenuWithDetails(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onAction: (AccountAction) -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        // First section
        DropdownMenuItem(
            text = { Text("Refresh") },
            leadingIcon = { Icon(Icons.Outlined.Refresh, contentDescription = null) },
            onClick = {
                onDismissRequest()
                onAction(AccountAction.Refresh)
            }
        )
        DropdownMenuItem(
            text = { Text("Remove Accounts") },
            leadingIcon = { Icon(Icons.Outlined.Remove, contentDescription = null) },
            onClick = {
                onDismissRequest()
                onAction(AccountAction.Remove)
            }
        )

        HorizontalDivider()

//        // Second section
//        DropdownMenuItem(
//            text = { Text("Send Feedback") },
//            leadingIcon = { Icon(Icons.Outlined.Feedback, contentDescription = null) },
//            trailingIcon = { Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = null) },
//            onClick = { /* Do something... */ }
//        )
//
//        HorizontalDivider()
//
//        // Third section
//        DropdownMenuItem(
//            text = { Text("About") },
//            leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = null) },
//            onClick = { /* Do something... */ }
//        )
        DropdownMenuItem(
            text = { Text("Settings") },
            leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            trailingIcon = {
                Icon(
                    Icons.AutoMirrored.Outlined.OpenInNew,
                    contentDescription = null
                )
            },
            onClick = {
                onDismissRequest()
                onAction(AccountAction.Settings)
            }
        )
    }
}
