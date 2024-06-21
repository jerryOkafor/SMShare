package com.jerryokafor.smshare.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.jerryokafor.smshare.theme.FillingSpacer
import com.jerryokafor.smshare.theme.OneHorizontalSpacer
import com.jerryokafor.smshare.theme.OneVerticalSpacer
import com.jerryokafor.smshare.theme.TwoHorizontalSpacer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun SettingsPreview() {
    MaterialTheme {
        Settings()
    }
}

@Composable
fun Settings(
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
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SettingsMenuSection(heading = "Accounts") {
                SettingsItem(title = "Push Notifications", icon = Icons.Default.Notifications)
                MeniItemDivider()
                SettingsItem(title = "Email Settings", icon = Icons.Default.Email)
                MeniItemDivider()
                SettingsItem("Change Password", icon = Icons.Default.Security)
                MeniItemDivider()
                SettingsItem("Support", icon = Icons.Default.Support)
                MeniItemDivider()
                SettingsItem("Refer a friend", icon = Icons.Default.IosShare)
                MeniItemDivider()
                SettingsItem("Add a new account", icon = Icons.Default.Add)
            }

            SettingsMenuSection(heading = "Subscriptions") {
                SettingsItem(title = "View Plans", icon = Icons.Default.FlashlightOn)
                MeniItemDivider()
                SettingsItem(
                    title = "Restore in-App Subscriptions",
                    icon = Icons.Default.FlashlightOn,
                )
            }

            SettingsMenuSection(heading = "Tools") {
                SettingsItem(title = "AI", icon = Icons.Default.Cloud)
                MeniItemDivider()
                SettingsItem(title = "Siri", icon = Icons.Default.Assistant)
            }

            SettingsMenuSection(heading = "Legal") {
                SettingsItem(title = "Privacy Policy", icon = Icons.Default.PrivacyTip)
                MeniItemDivider()
                SettingsItem(title = "Terms of use", icon = Icons.Default.PrivacyTip)
            }

            SettingsMenuSection(
                heading = "Hot zone",
                headingColor = MaterialTheme.colorScheme.error,
            ) {
                SettingsItem(
                    title = "Logout",
                    icon = Icons.AutoMirrored.Filled.Logout,
                    iconBackground = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    iconTintColor = MaterialTheme.colorScheme.error,
                )
                MeniItemDivider()
                SettingsItem(
                    title = "Delete Account",
                    icon = Icons.Default.Delete,
                    iconBackground = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    iconTintColor = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

const val settingRoute = "settings"

fun NavGraphBuilder.settingsScreen(
    onSetupTopAppBar: () -> Unit = {},
    onSetUpBottomAppBar: () -> Unit,
) {
    composable(settingRoute) {
        Settings(
            onSetupTopAppBar = onSetupTopAppBar,
            onSetUpBottomAppBar = onSetUpBottomAppBar,
        )
    }
}

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(route = settingRoute, navOptions = navOptions)
}

@Composable
private fun SettingsMenuSection(
    heading: String,
    headingColor: Color? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column {
        Text(
            text = heading,
            modifier = Modifier.padding(start = 16.dp),
            color = headingColor ?: Color.Unspecified,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
        )
        OneVerticalSpacer()
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
private fun MeniItemDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        thickness = 0.5.dp,
        modifier = modifier.padding(start = 60.dp, end = 8.dp),
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
    )
}

@Composable
private fun SettingsItem(
    title: String,
    icon: ImageVector = Icons.Default.Notifications,
    textColor: Color? = null,
    iconTintColor: Color? = null,
    iconBackground: Color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
    onClick: () -> Unit = {},
) {
    Surface(
        shape = RoundedCornerShape(2.dp),
        color = Color.Transparent,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OneHorizontalSpacer()
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = iconBackground,
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = icon,
                    contentDescription = "",
                    tint = iconTintColor ?: LocalContentColor.current,
                )
            }
            TwoHorizontalSpacer()
            Text(text = title, color = textColor ?: Color.Unspecified)
            FillingSpacer()
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "")
            OneHorizontalSpacer()
        }
    }
}
