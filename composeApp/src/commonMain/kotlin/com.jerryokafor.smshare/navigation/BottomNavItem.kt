@file:Suppress("InvalidPackageDeclaration", "MatchingDeclarationName")

package com.jerryokafor.smshare.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptions
import com.jerryokafor.smshare.screens.analytics.Analytics
import com.jerryokafor.smshare.screens.analytics.navigateToAnalytics
import com.jerryokafor.smshare.screens.drafts.Drafts
import com.jerryokafor.smshare.screens.drafts.navigateToDrafts
import com.jerryokafor.smshare.screens.posts.Posts
import com.jerryokafor.smshare.screens.posts.navigateToPosts
import com.jerryokafor.smshare.screens.settings.Settings
import com.jerryokafor.smshare.screens.settings.navigateToSettings
import kotlinx.serialization.Serializable

@Serializable
data object Auth

sealed interface BottomNavItem {
    val title: @Composable () -> String
    val icon: @Composable () -> Unit
    val isSelected: (navController: NavController) -> Boolean

    fun navigate(
        navController: NavController,
        navOptions: NavOptions,
    )

    data class PostsNavItem(
        override val title: @Composable () -> String = { "Posts" },
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.AutoMirrored.Filled.Message, contentDescription = null)
        },
        override val isSelected: (navController: NavController) -> Boolean = {
            it.currentDestination?.hierarchy?.any { it.hasRoute<Posts>() } == true
        },
    ) : BottomNavItem {
        override fun navigate(
            navController: NavController,
            navOptions: NavOptions,
        ) = navController.navigateToPosts(navOptions)
    }

    data class DraftsBottomNavItem(
        override val title: @Composable () -> String = { "Drafts" },
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        },
        override val isSelected: (navController: NavController) -> Boolean = {
            it.currentDestination?.hierarchy?.any { it.hasRoute<Drafts>() } == true
        },
    ) : BottomNavItem {
        override fun navigate(
            navController: NavController,
            navOptions: NavOptions,
        ) = navController.navigateToDrafts(navOptions)
    }

    data class AnalyticsBottomNavItem(
        override val title: @Composable () -> String = { "Analytics" },
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Analytics, contentDescription = null)
        },
        override val isSelected: (navController: NavController) -> Boolean = {
            it.currentDestination?.hierarchy?.any { it.hasRoute<Analytics>() } == true
        },
    ) : BottomNavItem {
        override fun navigate(
            navController: NavController,
            navOptions: NavOptions,
        ) = navController.navigateToAnalytics(navOptions)
    }

    data class SettingsBottomNavItem(
        override val title: @Composable () -> String = { "Settings" },
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        },
        override val isSelected: (navController: NavController) -> Boolean = {
            it.currentDestination?.hierarchy?.any { it.hasRoute<Settings>() } == true
        },
    ) : BottomNavItem {
        override fun navigate(
            navController: NavController,
            navOptions: NavOptions,
        ) = navController.navigateToSettings(navOptions)
    }
}
