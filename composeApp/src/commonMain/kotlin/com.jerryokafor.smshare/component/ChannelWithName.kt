package com.jerryokafor.smshare.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ChannelWithName(
    modifier: Modifier = Modifier,
    name: String,
    avatar: Painter,
    indicator: Painter,
    avatarSize: Dp = 60.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    contentDescription: String? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ChannelImage(
            modifier = Modifier.size(avatarSize).weight(1f),
            indicator = indicator,
            avatar = avatar,
            contentDescription = contentDescription,
        )

        Text(modifier = Modifier.weight(1f), text = name, style = textStyle)
    }
}
