package com.jerryokafor.smshare.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

sealed interface NavItem {
    val title: @Composable () -> String
    val icon: @Composable () -> Unit
    val onClick: (navController: NavController) -> Unit
    val isSelected: (navController: NavController) -> Boolean
    val route: () -> String

    data class Posts(
        override val title: @Composable () -> String = { "Posts" },
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.AutoMirrored.Filled.Message, contentDescription = null)
        },
        override val route: () -> String = { "posts" },
        override val onClick: (navController: NavController) -> Unit = {
            it.navigate(route())
        },
        override val isSelected: (navController: NavController) -> Boolean = {
            it.currentDestination?.route == route()
        },
    ) : NavItem

    data class Drafts(
        override val title: @Composable () -> String = { "Drafts" },
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        },
        override val route: () -> String = { "drafts" },
        override val onClick: (navController: NavController) -> Unit = {
            it.navigate(route())
        },
        override val isSelected: (navController: NavController) -> Boolean = {
            it.currentDestination?.route == route()
        },
    ) : NavItem

    data class Analytics(
        override val title: @Composable () -> String = { "Analytics" },
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Analytics, contentDescription = null)
        },
        override val route: () -> String = { "analytics" },
        override val onClick: (navController: NavController) -> Unit = {
            it.navigate(route())
        },
        override val isSelected: (navController: NavController) -> Boolean = {
            it.currentDestination?.route == route()
        },
    ) : NavItem

    data class Settings(
        override val title: @Composable () -> String = { "Settings" },
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        },
        override val route: () -> String = { "settings" },
        override val onClick: (navController: NavController) -> Unit = {
            it.navigate(route())
        },
        override val isSelected: (navController: NavController) -> Boolean = {
            it.currentDestination?.route == route()
        },
    ) : NavItem
}
