package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

sealed interface NavItem {
    val title: @Composable () -> String
    val icon: @Composable () -> Unit
    val onClick: (navigator: Navigator) -> Unit
    val isSelected: (Screen) -> Boolean


    data class Posts(
        override val title: @Composable () -> String = { "Posts" },

        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Message, contentDescription = null)
        },
        override val onClick: (navigator: Navigator) -> Unit = {
            it.push(screens.Posts())
        },
        override val isSelected: (Screen) -> Boolean = { it is screens.Posts }
    ) : NavItem

    data class Drafts(
        override val title: @Composable () -> String = { "Drafts" },

        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        },
        override val onClick: (navigator: Navigator) -> Unit = {
            it.push(screens.Drafts())
        },
        override val isSelected: (Screen) -> Boolean = { it is screens.Drafts }
    ) : NavItem

    data class Analytics(
        override val title: @Composable () -> String = { "Analytics" },

        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Analytics, contentDescription = null)
        },
        override val onClick: (navigator: Navigator) -> Unit = {
            it.push(screens.Analytics())
        },
        override val isSelected: (Screen) -> Boolean = { it is screens.Analytics }
    ) : NavItem

    data class Settings(
        override val title: @Composable () -> String = { "Settings" },

        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        },
        override val onClick: (navigator: Navigator) -> Unit = {
            it.push(screens.Settings())
        },
        override val isSelected: (Screen) -> Boolean = { it is screens.Settings }
    ) : NavItem

}
