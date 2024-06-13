package com.jerryokafor.smshare

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.jerryokafor.smshare.channel.ChannelAuthManager
import com.jerryokafor.smshare.component.ChannelWithName
import com.jerryokafor.smshare.component.NewChannelConnectionButton
import com.jerryokafor.smshare.component.iconIndicatorForAccountType
import com.jerryokafor.smshare.screens.navigation.SideNav
import com.jerryokafor.smshare.screens.navigation.SideNavMenuAction
import com.jerryokafor.smshare.navigation.NavItem
import com.jerryokafor.smshare.platform.SupportedPlatformType
import com.jerryokafor.smshare.screens.analytics.analyticsScreen
import com.jerryokafor.smshare.screens.compose.composeMessageScreen
import com.jerryokafor.smshare.screens.compose.navigateToCompose
import com.jerryokafor.smshare.screens.drafts.draftsScreen
import com.jerryokafor.smshare.screens.login.navigateToSignIn
import com.jerryokafor.smshare.screens.login.signInRoute
import com.jerryokafor.smshare.screens.login.signInScreen
import com.jerryokafor.smshare.screens.posts.navigateToPosts
import com.jerryokafor.smshare.screens.posts.postsScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import screens.addNewConnection.addNewConnectionScreen
import screens.createAccount.navigateToSignUp
import screens.createAccount.signUpRoute
import screens.createAccount.signUpScreen
import com.jerryokafor.smshare.screens.settings.settingsScreen
import com.jerryokafor.smshare.tags.navigateToTags
import com.jerryokafor.smshare.tags.tagsScreen
import org.jetbrains.compose.resources.painterResource
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.avatar6
import smshare.composeapp.generated.resources.ic_twitter

expect val DEV_SERVER_HOST: String

private val LightColorScheme = lightColorScheme(
    surface = Color(0xFFFFFFFF),
    background = Color(0xFFF8F9FE),
)
private val DarkColorScheme = darkColorScheme(
    surface = Color(0xFF14142B),
    background = Color(0xFF25253C),
)

@Composable
fun rememberAppState(
    navController: NavHostController,
    viewModel: AppViewModel,
): AppState = remember(navController) {
    AppState(
        navController = navController,
        viewModel = viewModel,
    )
}

@Stable
class AppState(
    private val navController: NavController,
    private val viewModel: AppViewModel,
) {
    val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: String?
        @Composable
        get() =
            when (currentDestination?.route) {
                "posts" -> "Posts"
                "drafts" -> "Drafts"
                "analytics" -> "Analytics"
                "settings" -> "Settings"
                else -> null
            }
}

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App(
    viewModel: AppViewModel = koinViewModel<AppViewModel>(),
    navController: NavHostController = rememberNavController(),
    appState: AppState = rememberAppState(navController, viewModel),
    onAppReady: () -> Unit = {},
    onNetChange: (Boolean) -> Unit = {},
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    KoinContext {
        val shouldUseDarkTheme by remember(isDarkTheme) { mutableStateOf(isDarkTheme) }

        val colorScheme = remember(shouldUseDarkTheme) {
            if (!shouldUseDarkTheme) {
                LightColorScheme
            } else {
                DarkColorScheme
            }
        }
        MaterialTheme(colorScheme = colorScheme) {
            Home(
                appState = appState,
                onNetChange = onNetChange,
                onAppReady = onAppReady,
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}

data class SMShareBottomAppBarState(val configure: @Composable () -> Unit)

data class BottomSheetState(val content: (@Composable () -> Unit)? = null)

data class SMShareTopAppBarState(val configure: (@Composable () -> Unit)? = null)

@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun Home(
    appState: AppState,
    onNetChange: (Boolean) -> Unit = {},
    viewModel: AppViewModel,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onAppReady: () -> Unit = {},
    onShowSnackbar: suspend (String, String?) -> Boolean = { msg, act ->
        snackbarHostState.showSnackbar(
            message = msg,
            actionLabel = act,
            duration = SnackbarDuration.Short,
        ) == SnackbarResult.ActionPerformed
    },
) {
    val windowSizeClass = calculateWindowSizeClass()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val shouldShowBottomNav = remember(windowSizeClass) {
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentAnAppReady by rememberUpdatedState(onAppReady)

    val channels by viewModel.channels.collectAsState()
    val appUiState by viewModel.userData.collectAsState()
    val isOnline by viewModel.isOnLine.collectAsState()
    val accounts by viewModel.accounts.collectAsState()

    val currentOnNetChange by rememberUpdatedState(onNetChange)
    LaunchedEffect(viewModel, isOnline) {
        currentOnNetChange(isOnline)
    }

    val isLoggedIn = when (val data = appUiState) {
        AppUiState.Loading -> false
        is AppUiState.Success -> data.user.isLoggedIn
    }

    val onMoreMenuClick: () -> Unit = {
        scope.launch {
            drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    val onMenuItemClick: (route: String) -> Unit = { route ->
        navController.navigate(route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().route!!) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    val mainTopAppBar: @Composable (() -> Unit) = {
        CenterAlignedTopAppBar(
            title = {
                var expanded by remember { mutableStateOf(false) }
                val scrollState = rememberScrollState()
                Box(
                    modifier = Modifier.wrapContentSize(Alignment.TopStart)
                ) {
                    TextButton(onClick = { expanded = true }) {
                        ChannelWithName(
                            modifier = Modifier.wrapContentSize(),
                            name = "Jerry Okafor",
                            avatarSize = 38.dp,
                            avatar = painterResource(Res.drawable.avatar6),
                            indicator = painterResource(Res.drawable.ic_twitter),
                        )
                    }

                    DropdownMenu(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        scrollState = scrollState
                    ) {
                        accounts.fastForEach { account ->
                            DropdownMenuItem(
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                                colors = MenuDefaults.itemColors(),
                                text = {
                                    ChannelWithName(
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 4.dp
                                        ),
                                        name = account.name,
                                        avatarSize = 38.dp,
                                        avatar = painterResource(Res.drawable.avatar6),
                                        indicator = iconIndicatorForAccountType(account.type),
                                    )
                                },
                                onClick = { expanded = false })
                        }
                    }
                    LaunchedEffect(expanded) {
                        if (expanded) {
                            // Scroll to show the bottom menu items.
                            scrollState.scrollTo(scrollState.maxValue)
                        }
                    }
                }

            },
            actions = {
                IconButton(onClick = onMoreMenuClick) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "More",
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )
    }

    val mainBottomNavigation: @Composable () -> Unit = {
        NavigationBar(modifier = Modifier.background(Color.Red)) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            listOf(
                NavItem.Posts(),
                NavItem.Drafts(),
                NavItem.Analytics(),
                NavItem.Settings(),
            ).fastForEach { navItem ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == navItem.route() } == true,
                    onClick = { onMenuItemClick(navItem.route()) },
                    icon = navItem.icon,
                    label = { Text(text = navItem.title()) },
                )
            }
        }
    }

    val channelAuthManager = koinInject<ChannelAuthManager>()
    var sheetContent by remember { mutableStateOf<BottomSheetState?>(null) }
    var topAppBarState by remember { mutableStateOf<SMShareTopAppBarState?>(null) }
    var bottomAppBarState by remember { mutableStateOf<SMShareBottomAppBarState?>(null) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    Box(modifier = Modifier.fillMaxSize()) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        SideNav(
                            accounts = accounts,
                            onClose = { sideNavMenAction ->
                                scope.launch { drawerState.close() }
                                    .invokeOnCompletion {
                                        when (sideNavMenAction) {
                                            SideNavMenuAction.Logout -> {
                                                viewModel.logout()
                                                navController.navigateToSignIn(
                                                    navOptions {
                                                        popUpTo(navController.graph.startDestinationRoute!!) {
                                                            inclusive = true
                                                        }
                                                        launchSingleTop = true
                                                    },
                                                )
                                            }

                                            SideNavMenuAction.AddNewConnection -> {
                                                sheetContent =
                                                    BottomSheetState {
                                                        Box(modifier = Modifier.height(400.dp)) {
                                                            LazyColumn(modifier = Modifier.padding(8.dp)) {
                                                                item { Spacer(Modifier.height(16.dp)) }
                                                                items(channels) { channel ->
                                                                    NewChannelConnectionButton(
                                                                        title = channel.name,
                                                                        description = channel.description,
                                                                        icon = channel.icon,
                                                                        onClick = {
                                                                            scope.launch {
                                                                                sheetState.hide()
                                                                                channelAuthManager
                                                                                    .authenticateUser(
                                                                                        channel,
                                                                                    )
                                                                            }
                                                                        },
                                                                    )
                                                                }

                                                                item { Spacer(Modifier.height(8.dp)) }
                                                            }
                                                        }
                                                    }
                                                scope.launch { sheetState.show() }
                                                    .invokeOnCompletion { }
                                            }

                                            null -> { // Do Nothing
                                            }

                                            SideNavMenuAction.ManageTags -> navController.navigateToTags()
                                        }
                                    }
                            },
                        )
                    }
                },
                gesturesEnabled = appState.currentTopLevelDestination != null,
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    ModalBottomSheetLayout(
                        sheetState = sheetState,
                        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        sheetBackgroundColor = MaterialTheme.colorScheme.background,
                        sheetContent = {
                            sheetContent?.content?.invoke()
                        },
                        content = {
                            Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                snackbarHost = { SnackbarHost(snackbarHostState) },
                                topBar = {
                                    if (isLoggedIn) {
                                        topAppBarState?.configure?.invoke()
                                    }
                                },
                                content = { innerPadding ->
                                    Row(
                                        modifier = Modifier.fillMaxSize()
                                            .padding(
                                                top = innerPadding.calculateTopPadding(),
                                                end = innerPadding.calculateEndPadding(
                                                    LayoutDirection.Ltr,
                                                ),
                                                start = innerPadding.calculateStartPadding(
                                                    LayoutDirection.Ltr,
                                                ),
                                                bottom = innerPadding.calculateBottomPadding(),
                                            )
                                            .consumeWindowInsets(innerPadding),
                                    ) {
                                        if (!shouldShowBottomNav && appState.currentTopLevelDestination != null && bottomAppBarState != null) {
                                            NavigationRail(modifier = Modifier.fillMaxHeight()) {
                                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                                val currentDestination =
                                                    navBackStackEntry?.destination
                                                listOf(
                                                    NavItem.Posts(),
                                                    NavItem.Drafts(),
                                                    NavItem.Analytics(),
                                                    NavItem.Settings(),
                                                ).fastForEach { navItem ->
                                                    NavigationRailItem(
                                                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route() } == true,
                                                        onClick = { onMenuItemClick(navItem.route()) },
                                                        icon = navItem.icon,
                                                        label = { Text(text = navItem.title()) },
                                                    )
                                                }
                                            }
                                        }

                                        val onSetupTopAppBar: (SMShareTopAppBarState?) -> Unit =
                                            { topAppBarState = it }
                                        NavHost(
                                            modifier = Modifier.fillMaxSize(),
                                            navController = navController,
                                            startDestination = "splash",
                                        ) {
                                            composable("splash") {
                                                Box(modifier = Modifier.fillMaxSize()) {
                                                    CircularProgressIndicator(
                                                        modifier =
                                                        Modifier.size(24.dp)
                                                            .align(Alignment.Center),
                                                        strokeWidth = 1.dp,
                                                        strokeCap = StrokeCap.Round,
                                                    )
                                                }
                                            }
                                            // Auth
                                            signInScreen(
                                                onSetupTopAppBar = onSetupTopAppBar,
                                                onCreateAccountClick = {
                                                    navController.navigateToSignUp(
                                                        navOptions =
                                                        navOptions {
                                                            popUpTo(signInRoute) {
                                                                inclusive = true
                                                            }
                                                            launchSingleTop = true
                                                        },
                                                    )
                                                },
                                                onLoginComplete = {
                                                    navController.navigateToPosts(
                                                        navOptions {
                                                            popUpTo(signInRoute) {
                                                                inclusive = true
                                                            }
                                                            launchSingleTop = true
                                                        },
                                                    )
                                                },
                                                onShowSnackbar = onShowSnackbar,
                                                onSetUpBottomAppBar = { bottomAppBarState = it },
                                            )

                                            signUpScreen(
                                                onSetupTopAppBar = onSetupTopAppBar,
                                                onLoginClick = {
                                                    navController.navigateToSignIn(
                                                        navOptions {
                                                            popUpTo(signUpRoute) {
                                                                inclusive = true
                                                            }
                                                            launchSingleTop = true
                                                        },
                                                    )
                                                },
                                                onShowSnackbar = onShowSnackbar,
                                                onSetUpBottomAppBar = { bottomAppBarState = it },
                                            )
                                            postsScreen(
                                                onSetupTopAppBar = {
                                                    topAppBarState =
                                                        SMShareTopAppBarState(configure = mainTopAppBar)
                                                },
                                                onSetUpBottomAppBar = {
                                                    bottomAppBarState =
                                                        SMShareBottomAppBarState(
                                                            configure = mainBottomNavigation,
                                                        )
                                                }
                                            )
                                            draftsScreen(
                                                onSetupTopAppBar = {
                                                    topAppBarState =
                                                        SMShareTopAppBarState(configure = mainTopAppBar)
                                                },
                                                onSetUpBottomAppBar = {
                                                    bottomAppBarState =
                                                        SMShareBottomAppBarState(
                                                            configure = mainBottomNavigation,
                                                        )
                                                }
                                            )
                                            analyticsScreen(
                                                onSetupTopAppBar = {
                                                    topAppBarState =
                                                        SMShareTopAppBarState(configure = mainTopAppBar)
                                                },
                                                onSetUpBottomAppBar = {
                                                    bottomAppBarState =
                                                        SMShareBottomAppBarState(
                                                            configure = mainBottomNavigation,
                                                        )
                                                }
                                            )
                                            settingsScreen(
                                                onSetupTopAppBar = {
                                                    topAppBarState =
                                                        SMShareTopAppBarState(configure = mainTopAppBar)
                                                },
                                                onSetUpBottomAppBar = {
                                                    bottomAppBarState =
                                                        SMShareBottomAppBarState(
                                                            configure = mainBottomNavigation,
                                                        )
                                                }
                                            )

                                            // Compose message
                                            composeMessageScreen(
                                                onSetupTopAppBar = onSetupTopAppBar,
                                                onShowSnackbar = onShowSnackbar,
                                                onSetUpBottomAppBar = { bottomAppBarState = it },
                                                onCancel = { navController.popBackStack() },
                                            )

                                            // Add new channel connection
                                            addNewConnectionScreen(
                                                onBackPress = {
                                                    navController.popBackStack()
                                                },
                                            )

                                            //tags
                                            tagsScreen(
                                                onSetupTopAppBar = { topAppBarState = it },
                                                onSetUpBottomAppBar = { bottomAppBarState = it })
                                        }
                                    }
                                },
                                floatingActionButton = {
                                    AnimatedVisibility(appState.currentTopLevelDestination != null) {
                                        ExtendedFloatingActionButton(onClick = { navController.navigateToCompose() }) {
                                            Row(
                                                modifier = Modifier,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Edit,
                                                    contentDescription = "",
                                                )
                                                Spacer(modifier = Modifier.width(16.dp))
                                                Text("Compose")
                                            }
                                        }
                                    }
                                },
                                bottomBar = {
                                    bottomAppBarState?.let { appBar -> appBar.configure() }
                                },
                            )
                        },
                    )
                }
            }
        }

        LaunchedEffect(appUiState) {
            if (appUiState is AppUiState.Success) {
                currentAnAppReady()
                if ((appUiState as AppUiState.Success).user.isLoggedIn) {
                    navController.navigateToPosts()
                } else {
                    navController.navigateToSignIn()
                }
            }
        }
        val isPlatformIOSOrAndroid = when (val state = appUiState) {
            AppUiState.Loading -> false
            is AppUiState.Success -> state.platform.type == SupportedPlatformType.Android
                    || state.platform.type == SupportedPlatformType.iOS
        }
        AnimatedVisibility(!isOnline && isPlatformIOSOrAndroid) {
            Box(modifier = Modifier.fillMaxWidth().height(48.dp).background(Color.Red))
        }
    }
}
