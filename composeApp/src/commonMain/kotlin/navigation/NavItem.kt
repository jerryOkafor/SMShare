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
import screens.FourthScreen
import screens.HomeScreen
import screens.SecondScreen
import screens.ThirdScreen

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
            it.push(HomeScreen())
        },
        override val isSelected: (Screen) -> Boolean = { it is HomeScreen }
    ) : NavItem

    data class Drafts(
        override val title: @Composable () -> String = { "Drafts" },

        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
        },
        override val onClick: (navigator: Navigator) -> Unit = {
            it.push(SecondScreen())
        },
        override val isSelected: (Screen) -> Boolean = { it is SecondScreen }
    ) : NavItem

    data class Analytics(
        override val title: @Composable () -> String = { "Analytics" },

        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Analytics, contentDescription = null)
        },
        override val onClick: (navigator: Navigator) -> Unit = {
            it.push(ThirdScreen())
        },
        override val isSelected: (Screen) -> Boolean = { it is ThirdScreen }
    ) : NavItem

    data class Settings(
        override val title: @Composable () -> String = { "Settings" },

        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        },
        override val onClick: (navigator: Navigator) -> Unit = {
            it.push(FourthScreen())
        },
        override val isSelected: (Screen) -> Boolean = { it is FourthScreen }
    ) : NavItem

}
