package com.jerryokafor.smshare.screens.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jerryokafor.smshare.SMShareBottomAppBarState
import com.jerryokafor.smshare.SMShareTopAppBarState
import com.jerryokafor.smshare.component.ChannelImage
import com.jerryokafor.smshare.component.ChannelItemMenu
import com.jerryokafor.smshare.component.SMSShareTextButton
import com.jerryokafor.smshare.component.iconForChannel
import com.jerryokafor.smshare.theme.FillingSpacer
import com.jerryokafor.smshare.theme.OneVerticalSpacer
import com.jerryokafor.smshare.theme.ThreeVerticalSpacer
import com.jerryokafor.smshare.theme.TwoHorizontalSpacer
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.avatar6

@Preview
@Composable
private fun ComposeMessagePreview() {
    MaterialTheme {
        ComposeMessage()
    }
}

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
@Composable
fun ComposeMessage(
    onSetupTopAppBar: (SMShareTopAppBarState) -> Unit = {},
    onSetUpBottomAppBar: (SMShareBottomAppBarState) -> Unit = { _ -> },
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    onCancel: () -> Unit = {},
) {
    val viewModel: ComposeMessageViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val onAttachContent: () -> Unit = {
        scope.launch { onShowSnackbar("Coming soon", null) }
    }
    val onOpenDraft: () -> Unit = {
        scope.launch { onShowSnackbar("Coming soon", null) }
    }
    val onAddTagClick: () -> Unit = {
        scope.launch { onShowSnackbar("Coming soon", null) }
    }

    val currentOnSetupTopAppBar by rememberUpdatedState(onSetupTopAppBar)
    val currentNnSetUpBottomAppBar by rememberUpdatedState(onSetUpBottomAppBar)
    LaunchedEffect(true) {
        currentOnSetupTopAppBar(
            SMShareTopAppBarState(
                title = {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(uiState.targetChannels.filter { it.isSelected }) { channel ->
                            ChannelImage(
                                modifier = Modifier.size(40.dp),
                                avatar = painterResource(Res.drawable.avatar6),
                                indicator = iconForChannel(channel.type),
                                contentDescription = "",
                            )
                        }

                        item {
                            IconButton(onClick = { showBottomSheet = true }) {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = "Add new target channel",
                                )
                            }
                        }
                    }
                },
                actions = {},
            ),
        )

        currentNnSetUpBottomAppBar(
            SMShareBottomAppBarState {
                BottomAppBar(
                    modifier = Modifier.imePadding(),
                    actions = {
                        SMSShareTextButton(onClick = onCancel) {
                            Text("Cancel")
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { /* do something */ },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, "Send Message")
                        }
                    },
                )
            },
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val state = rememberRichTextState()
        RichTextEditor(
            state = state,
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            minLines = 3,
            maxLines = 10,
            textStyle = MaterialTheme.typography.bodyLarge,
            colors =
                RichTextEditorDefaults.richTextEditorColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            placeholder = { Text("What are we doing today?") },
            shape = RichTextEditorDefaults.outlinedShape,
        )
        // Todo: Add character limit counter
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ComposeControlButton(
                imageVector = Icons.Default.AttachFile,
                text = "Attach",
                onClick = onAttachContent,
            )
            ComposeControlButton(
                imageVector = Icons.Default.FolderOpen,
                text = "Open Drafts",
                onClick = onOpenDraft,
            )
            ComposeControlButton(
                imageVector = Icons.Default.Tag,
                text = "Add Tags",
                onClick = onAddTagClick,
            )
        }

        FillingSpacer()
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            // Sheet content
            LazyColumn(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("Select target channels")
                        FillingSpacer()
                        SMSShareTextButton(onClick = {
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                showBottomSheet = false
                            }
                        }) {
                            Text("Done")
                        }
                    }
                    HorizontalDivider()
                    OneVerticalSpacer()
                }
                items(uiState.targetChannels) { channel ->
                    ChannelItemMenu(
                        modifier = Modifier.padding(end = 16.dp),
                        name = channel.name,
                        color = Color.Transparent,
                        avatar = painterResource(Res.drawable.avatar6),
                        indicator = iconForChannel(channel.type),
                        onClick = {
                            if (channel.isSelected) {
                                viewModel.removeTargetChannel(channel.type)
                            } else {
                                viewModel.addNewTargetChannel(channel.type)
                            }
                        },
                    ) {
                        AnimatedVisibility(channel.isSelected) {
                            Icon(
                                tint = MaterialTheme.colorScheme.secondary,
                                imageVector = Icons.Default.Check,
                                contentDescription = "",
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier =
                            Modifier.height(0.5.dp)
                                .padding(start = 80.dp, end = 8.dp),
                    )
                }

                item {
                    HorizontalDivider(
                        modifier =
                            Modifier.height(0.5.dp)
                                .padding(start = 80.dp, end = 8.dp),
                    )
                    Surface(onClick = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            showBottomSheet = false
                            scope.launch {
                                onShowSnackbar("Coming soon", null)
                            }
                        }
                    }, color = Color.Transparent) {
                        Row(
                            modifier =
                                Modifier.fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surface,
                            ) {
                                Icon(
                                    modifier = Modifier.padding(12.dp).size(24.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Connect a new channel",
                                )
                            }
                            TwoHorizontalSpacer()
                            Text("Connect a new channel")
                            FillingSpacer()
                            Icon(
                                tint = MaterialTheme.colorScheme.secondary,
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "",
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier =
                            Modifier.height(0.5.dp)
                                .padding(start = 80.dp, end = 8.dp),
                    )
                }
                item { ThreeVerticalSpacer() }
            }
        }
    }
}

@Composable
fun ComposeControlButton(
    imageVector: ImageVector,
    text: String,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    SMSShareTextButton(onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = imageVector,
                contentDescription = contentDescription ?: text,
            )
            Text(text = text, style = MaterialTheme.typography.labelMedium)
        }
    }
}

val composeMessageRoute = "compose"

fun NavGraphBuilder.composeMessageScreen(
    onSetupTopAppBar: (SMShareTopAppBarState) -> Unit = {},
    onSetUpBottomAppBar: (SMShareBottomAppBarState) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    onCancel: () -> Unit,
) {
    composable(composeMessageRoute) {
        ComposeMessage(
            onSetupTopAppBar = onSetupTopAppBar,
            onSetUpBottomAppBar = onSetUpBottomAppBar,
            onShowSnackbar = onShowSnackbar,
            onCancel = onCancel,
        )
    }
}

fun NavController.navigateToCompose(navOptions: NavOptions? = null) {
    navigate(composeMessageRoute, navOptions)
}
