package component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SideNav(onClose: () -> Unit, onAddChanelClick: () -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier.width(280.dp),
        drawerContainerColor = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "SM Share",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        MenuGroup {
            Column(modifier = Modifier.fillMaxWidth()) {
                SideNavMenu(
                    title = "Content",
                    subTitle = "Save your ideas"
                ) {
                    onClose()
                }
                Divider(modifier = Modifier.height(0.5.dp).padding(horizontal = 8.dp))
                SideNavMenu(
                    title = "Calendar",
                    subTitle = "See your schedule wide"
                ) {
                    onClose()
                }
            }
        }

        Text("Channels", modifier = Modifier.padding(16.dp))
        MenuGroup {
            Column {
                ChannelItemMenu(
                    name = "@Noms0",
                    avatar = painterResource("avatar6.png"),
                    indicator = painterResource("ic_twitter.xml"),
                    onClick = { onClose() }
                )

                Divider(modifier = Modifier.height(0.5.dp).padding(start = 80.dp, end = 8.dp))
                ChannelItemMenu(
                    name = "Jerry Okafor",
                    avatar = painterResource("avatar6.png"),
                    indicator = painterResource("ic_linkedin.xml"),
                    onClick = { onClose() }
                )
                Divider(modifier = Modifier.height(0.5.dp).padding(start = 80.dp, end = 8.dp))
                ChannelItemMenu(
                    name = "Jerry Hanks",
                    avatar = painterResource("avatar6.png"),
                    indicator = painterResource("ic_facebook.xml"),
                    onClick = { onClose() }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        MenuGroup {
            Column {
                MoreMenuItem(
                    title = "Add Channel",
                    icon = Icons.Default.Add,
                    onClick = onAddChanelClick
                )
                Divider(modifier = Modifier.height(0.5.dp))
                MoreMenuItem(
                    title = "Get more with Rexi",
                    icon = Icons.Default.ElectricBolt
                ) {
                    onClose()
                }

                MoreMenuItem(
                    title = "Start a page",
                    icon = Icons.Default.Bookmark
                ) {
                    onClose()
                }
                Divider(modifier = Modifier.height(0.5.dp))
                MoreMenuItem(
                    title = "Manage Tags",
                    icon = Icons.Default.Tag
                ) {
                    onClose()
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("More", modifier = Modifier.padding(16.dp))
        MenuGroup {
            MoreMenuItem(
                title = "Logout",
                color = Color.Red,
                icon = Icons.Default.Logout
            ) {
                onClose()
            }
        }

    }
}

@Composable
fun MenuGroup(content: @Composable () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 5.dp),
        content = content
    )

}

@Composable
fun SideNavMenu(title: String, subTitle: String, onClick: () -> Unit = {}) {
    Surface(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = subTitle, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.width(16.dp))
            FillingSpacer()
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelItemMenu(
    modifier: Modifier = Modifier,
    name: String,
    avatar: Painter,
    indicator: Painter,
    avatarSize: Dp = 40.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    contentDescription: String? = null,
    onClick: () -> Unit = {}
) {
    Surface(onClick = onClick) {
        Row(
            modifier = modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChannelWithName(
                modifier = Modifier.weight(4f),
                name = name,
                avatar = avatar,
                indicator = indicator,
                avatarSize = avatarSize,
                textStyle = textStyle,
                contentDescription = contentDescription
            )
            FillingSpacer()
            Badge(
                modifier = Modifier,
                containerColor = Color.Gray
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "2"
                )
            }
        }
    }
}


@Composable
fun MoreMenuItem(
    title: String, icon: ImageVector,
    color: Color = Color.Blue,
    onClick: () -> Unit = {}
) {
    Surface(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = color,
                shape = RoundedCornerShape(10)
            ) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White
                )

            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.labelLarge)
            FillingSpacer()
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun ColumnScope.FillingSpacer() {
    Spacer(modifier = Modifier.weight(1f))
}


@Composable
fun RowScope.FillingSpacer() {
    Spacer(modifier = Modifier.weight(1f))
}