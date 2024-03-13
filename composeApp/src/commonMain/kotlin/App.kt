import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import component.SideNav
import kotlinx.coroutines.launch
import navigation.NavItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import screens.Posts
import screens.AddNewConnectionScreen
import screens.Settings
import screens.Drafts
import screens.Analytics
import screens.BottomSheetSample
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.main_nav_title_analytics
import smshare.composeapp.generated.resources.main_nav_title_drafts
import smshare.composeapp.generated.resources.main_nav_title_posts
import smshare.composeapp.generated.resources.main_nav_title_settings
import kotlin.jvm.Transient

private val LightColorScheme = lightColorScheme(
    surface = Color(0xFFFFFFFF),
    background = Color(0xFFF8F9FE),
)
private val DarkColorScheme = darkColorScheme(
    surface = Color(0xFF14142B),
    background = Color(0xFF25253C),
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App(isDarkTheme: Boolean = isSystemInDarkTheme()) {
    val shouldUseDarkTheme by remember(isDarkTheme) { mutableStateOf(isDarkTheme) }

    val colorScheme = remember(shouldUseDarkTheme) {
        if (!shouldUseDarkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        }
    }

    KoinContext {
        MaterialTheme(colorScheme = colorScheme) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val onMoreMenuClick: () -> Unit = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }

            BottomSheetNavigator(
                sheetShape = MaterialTheme.shapes.large,
                sheetElevation = 2.dp,
                skipHalfExpanded = true,
                sheetBackgroundColor = MaterialTheme.colorScheme.background,
                sheetContentColor = MaterialTheme.colorScheme.onBackground,
            ) { _ ->
                Navigator(screen = HomeDashboard(onMoreMenuClick = onMoreMenuClick)) { mainNavigator ->
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        ModalNavigationDrawer(
                            drawerState = drawerState,
                            drawerContent = {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                    SideNav(
                                        onClose = { scope.launch { drawerState.close() } },
                                        onAddChanelClick = {
                                            scope.launch { drawerState.close() }
                                            mainNavigator.push(AddNewConnectionScreen())
                                        })
                                }
                            },
                            gesturesEnabled = true
                        ) {
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                CurrentScreen()
                            }

                        }
                    }
                }
            }
        }
    }
}


class HomeDashboard(@Transient private val onMoreMenuClick: () -> Unit) : Screen {
    @OptIn(
        ExperimentalMaterial3Api::class,
        ExperimentalMaterial3WindowSizeClassApi::class,
        ExperimentalResourceApi::class
    )
    @Composable
    override fun Content() {
        val windowSizeClass = calculateWindowSizeClass()

        val shouldShowBottomBar: Boolean = remember(windowSizeClass) {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
        }

        Navigator(Posts()) { homeNavigator ->
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

            val title = when (homeNavigator.lastItem) {
                is Posts -> stringResource(Res.string.main_nav_title_posts)
                is Drafts -> stringResource(Res.string.main_nav_title_drafts)
                is Analytics -> stringResource(Res.string.main_nav_title_analytics)
                is Settings -> stringResource(Res.string.main_nav_title_settings)
                else -> ""
            }
            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        actions = {
                            IconButton(onClick = onMoreMenuClick) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "More"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                },
                content = { innerPadding ->
                    Row(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                    ) {
                        if (!shouldShowBottomBar) {
                            NavigationRail(modifier = Modifier.fillMaxHeight()) {
                                listOf(
                                    NavItem.Posts(),
                                    NavItem.Drafts(),
                                    NavItem.Analytics(),
                                    NavItem.Settings()
                                ).fastForEach { navItem ->
                                    NavigationRailItem(
                                        selected = navItem.isSelected(homeNavigator.lastItem),
                                        onClick = { navItem.onClick(homeNavigator) },
                                        icon = navItem.icon,
                                        label = { Text(text = navItem.title()) }
                                    )
                                }

                            }
                        }
                        Box(modifier = Modifier.fillMaxSize()) {
                            CurrentScreen()
                        }
                    }
                },
                bottomBar = {
                    if (shouldShowBottomBar) {
                        NavigationBar(modifier = Modifier.background(Color.Red)) {
                            listOf(
                                NavItem.Posts(),
                                NavItem.Drafts(),
                                NavItem.Analytics(),
                                NavItem.Settings()
                            ).fastForEach { navItem ->
                                NavigationBarItem(
                                    selected = navItem.isSelected(homeNavigator.lastItem),
                                    onClick = { navItem.onClick(homeNavigator) },
                                    icon = navItem.icon,
                                    label = { Text(text = navItem.title()) }
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}



