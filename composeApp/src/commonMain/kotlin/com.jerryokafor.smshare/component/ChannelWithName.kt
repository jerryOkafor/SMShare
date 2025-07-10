package com.jerryokafor.smshare.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jerryokafor.smshare.core.model.AccountType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.avatar6

@Preview
@Composable
fun ChannelWithNamePreview() {
    ChannelWithName(
        modifier = Modifier.padding(
            horizontal = 8.dp,
            vertical = 4.dp,
        ),
        name = "LinkedIn",
        avatarSize = 38.dp,
        avatar = painterResource(Res.drawable.avatar6),
        indicator = iconIndicatorForAccountType(
            AccountType.LINKEDIN,
        ),
    )


}
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
            modifier = Modifier.size(avatarSize).aspectRatio(1f),
            indicator = indicator,
            avatar = avatar,
            contentDescription = contentDescription,
        )

        Text(modifier = Modifier, text = name, style = textStyle)
    }
}
