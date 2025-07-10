package com.jerryokafor.smshare.screens.addNewConnection

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jerryokafor.smshare.channel.ChannelConfigResource
import com.jerryokafor.smshare.core.domain.ChannelConfig
import com.jerryokafor.smshare.component.NewChannelConnectionButton
import com.jerryokafor.smshare.theme.HalfVerticalSpacer
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewConnectionChannel(
    channels: List<ChannelConfigResource>,
    onChannelClick: (ChannelConfig) -> Unit,
    sheetState: SheetState,
    onCloseBottomSheet: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                onCloseBottomSheet()
            }
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(channels) { channel ->
                HalfVerticalSpacer()
                NewChannelConnectionButton(
                    title = channel.name,
                    description = channel.description,
                    icon = channel.icon,
                    onClick = { onChannelClick(channel) },
                )
                HalfVerticalSpacer()    
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddNewConnectionChannelPreview() {
    AddNewConnectionChannel(
        channels = listOf(),
        onChannelClick = {},
        sheetState = rememberModalBottomSheetState { true },
        onCloseBottomSheet = {},
    )
}
