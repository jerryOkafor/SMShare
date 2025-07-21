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

package com.jerryokafor.smshare.screens.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.jerryokafor.smshare.component.ChannelWithName
import com.jerryokafor.smshare.component.iconIndicatorForAccountType
import com.jerryokafor.smshare.core.model.AccountAndProfile
import com.jerryokafor.smshare.theme.FillingSpacer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.avatar6
import smshare.composeapp.generated.resources.main_app_name
import smshare.composeapp.generated.resources.title_accounts
import smshare.composeapp.generated.resources.title_add_account
import smshare.composeapp.generated.resources.title_logout
import smshare.composeapp.generated.resources.title_manage_accounts
import smshare.composeapp.generated.resources.title_manage_tags
import smshare.composeapp.generated.resources.title_more
import kotlin.math.min

sealed interface SideNavMenuAction {
    data object Logout : SideNavMenuAction

    data class SelectAccount(val accountAndProfile: AccountAndProfile) : SideNavMenuAction

    data object AddNewConnection : SideNavMenuAction

    data object ManageTags : SideNavMenuAction

    data object ManageAccounts : SideNavMenuAction
}

fun spacedByWithFooter(space: Dp) = object : Arrangement.Vertical {
    override val spacing = space

    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        outPositions: IntArray,
    ) {
        if (sizes.isEmpty()) return
        val spacePx = space.roundToPx()

        var occupied = 0
        var lastSpace = 0

        sizes.forEachIndexed { index, size ->

            if (index == sizes.lastIndex) {
                outPositions[index] = totalSize - size
            } else {
                outPositions[index] = min(occupied, totalSize - size)
            }
            lastSpace = min(spacePx, totalSize - outPositions[index] - size)
            occupied = outPositions[index] + size + lastSpace
        }
        occupied -= lastSpace
    }
}

fun drawerContent(
    accounts: List<AccountAndProfile>,
    onCloseSidNav: (SideNavMenuAction?) -> Unit
): @Composable () -> Unit = {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Ltr,
    ) {
        SideNav(
            accounts = accounts,
            onClose = onCloseSidNav,
        )
    }
}

@Composable
fun SideNav(
    accounts: List<AccountAndProfile>,
    onClose: (SideNavMenuAction?) -> Unit,
) {
    Box(Modifier.fillMaxSize().clickable(true) {
        onClose(null)
    }) {
        ModalDrawerSheet(
            modifier = Modifier.width(280.dp).align(Alignment.CenterEnd),
            drawerContainerColor = MaterialTheme.colorScheme.background,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = spacedByWithFooter(0.dp),
            ) {
                item {
                    Text(
                        text = stringResource(Res.string.main_app_name),
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 8.dp,
                        ),
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    MenuGroup {
                        SideNavMenu(
                            title = "Content",
                            subTitle = "Save your ideas",
                        ) {
                            onClose(null)
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            thickness = 0.5.dp,
                        )
                        SideNavMenu(
                            title = "Calendar",
                            subTitle = "See your schedule wide",
                        ) {
                            onClose(null)
                        }
                    }
                }

                item {
                    Text(
                        text = stringResource(Res.string.title_accounts),
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 8.dp,
                        ),
                    )


                    MenuGroup {
                        accounts.forEachIndexed { index, accountAndProfile ->
                            val (account, profile) = accountAndProfile
                            ChannelItemMenu(
                                name = account.name,
                                avatar = profile.picture ?: "",
                                postsCount = account.postsCount,
                                indicator = iconIndicatorForAccountType(account.type),
                                onClick = {
                                    onClose(
                                        SideNavMenuAction.SelectAccount(accountAndProfile)
                                    )
                                },
                            )

                            // Add divider except for the last item
                            if (index < accounts.size - 1) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .height(0.5.dp)
                                        .padding(start = 80.dp, end = 8.dp),
                                )
                            }
                        }

                        if (accounts.isEmpty()) {
                            Row(
                                modifier = Modifier
                                    .padding(
                                        vertical = 8.dp,
                                        horizontal = 16.dp,
                                    ).fillParentMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) { Text("No Account", color = MaterialTheme.colorScheme.error) }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    MenuGroup {
                        MoreMenuItem(
                            title = stringResource(Res.string.title_add_account),
                            icon = Icons.Default.Add,
                            onClick = { onClose(SideNavMenuAction.AddNewConnection) },
                        )
                        HorizontalDivider(thickness = 0.5.dp)
                        MoreMenuItem(
                            title = stringResource(Res.string.title_manage_accounts),
                            icon = Icons.Default.ManageAccounts,
                            onClick = { onClose(SideNavMenuAction.ManageAccounts) },
                        )
                        MoreMenuItem(
                            title = stringResource(Res.string.title_manage_tags),
                            icon = Icons.Default.Tag,
                            onClick = { onClose(SideNavMenuAction.ManageTags) },
                        )
                    }
                }

                item {
                    Text(
                        text = stringResource(Res.string.title_more),
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 8.dp,
                        ),
                    )
                    MenuGroup(modifier = Modifier) {
                        MoreMenuItem(
                            title = stringResource(Res.string.title_logout),
                            color = Color.Red,
                            icon = Icons.AutoMirrored.Filled.Logout,
                            onClick = { onClose(SideNavMenuAction.Logout) },
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun MenuGroup(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme
            .surfaceColorAtElevation(elevation = 5.dp)
            .copy(alpha = 0.5f),
        content = {
            Column {
                content()
            }
        },
    )
}

@Composable
fun SideNavMenu(
    title: String,
    subTitle: String,
    onClick: () -> Unit = {},
) {
    Surface(onClick = onClick) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
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

@Composable
fun ChannelItemMenu(
    modifier: Modifier = Modifier,
    name: String,
    postsCount: Int = 0,
    avatar: String,
    indicator: Painter,
    avatarSize: Dp = 40.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = MaterialTheme.colorScheme.surface,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
    trailingContent: @Composable (() -> Unit) = {
        if (postsCount > 1) {
            Badge(
                modifier = Modifier,
                containerColor = Color.Gray,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "$postsCount",
                )
            }
        }
    },
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(avatar)
            .error { it.placeholder() }
            .build(),
        placeholder = painterResource(Res.drawable.avatar6),
        contentScale = ContentScale.Crop,
    )
    
    Surface(onClick = onClick, color = color) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ChannelWithName(
                modifier = Modifier.weight(4f),
                name = name,
                avatar = painter,
                indicator = indicator,
                avatarSize = avatarSize,
                textStyle = textStyle,
                contentDescription = contentDescription,
            )
            FillingSpacer()
            trailingContent()
        }
    }
}

@Composable
fun MoreMenuItem(
    title: String,
    icon: ImageVector,
    color: Color = MaterialTheme.colorScheme.background,
    onClick: () -> Unit = {},
) {
    Surface(onClick = onClick) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                color = color,
                shape = RoundedCornerShape(10),
            ) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
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
