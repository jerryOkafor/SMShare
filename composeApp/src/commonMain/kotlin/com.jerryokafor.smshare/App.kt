package com.jerryokafor.smshare

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.jerryokafor.smshare.screens.manageAccounts.installAccountsScreen
import com.jerryokafor.smshare.screens.manageAccounts.navigateToManageAccounts
import com.jerryokafor.smshare.core.domain.ChannelConfig
import com.jerryokafor.smshare.channel.ExternalUriHandler
import com.jerryokafor.smshare.component.ChannelWithName
import com.jerryokafor.smshare.component.iconIndicatorForAccountType
import com.jerryokafor.smshare.navigation.Auth
import com.jerryokafor.smshare.navigation.BottomNavItem
import com.jerryokafor.smshare.platform.SupportedPlatformType
import com.jerryokafor.smshare.screens.analytics.Analytics
import com.jerryokafor.smshare.screens.analytics.analyticsScreen
import com.jerryokafor.smshare.screens.auth.AuthDestinations
import com.jerryokafor.smshare.screens.auth.installAuth
import com.jerryokafor.smshare.screens.auth.navigateToSignIn
import com.jerryokafor.smshare.screens.auth.navigateToSignUp
import com.jerryokafor.smshare.screens.compose.composeMessageScreen
import com.jerryokafor.smshare.screens.compose.navigateToCompose
import com.jerryokafor.smshare.screens.drafts.Drafts
import com.jerryokafor.smshare.screens.drafts.draftsScreen
import com.jerryokafor.smshare.screens.navigation.SideNavMenuAction
import com.jerryokafor.smshare.screens.navigation.drawerContent
import com.jerryokafor.smshare.screens.posts.Posts
import com.jerryokafor.smshare.screens.posts.installPosts
import com.jerryokafor.smshare.screens.posts.navigateToPosts
import com.jerryokafor.smshare.screens.settings.Settings
import com.jerryokafor.smshare.screens.settings.settingsScreen
import com.jerryokafor.smshare.screens.manageTags.navigateToTags
import com.jerryokafor.smshare.screens.manageTags.tagsScreen
import io.ktor.http.Url
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import com.jerryokafor.smshare.screens.addNewConnection.AddNewConnectionChannel
import smshare.composeapp.generated.resources.Res
import smshare.composeapp.generated.resources.avatar6
import smshare.composeapp.generated.resources.main_nav_title_analytics
import smshare.composeapp.generated.resources.main_nav_title_drafts
import smshare.composeapp.generated.resources.main_nav_title_posts
import smshare.composeapp.generated.resources.main_nav_title_settings

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
    shouldUseNavRail: Boolean,
): AppState = remember(navController, viewModel, shouldUseNavRail) {
    AppState(
        navController = navController,
        viewModel = viewModel,
        shouldUseNavRail = shouldUseNavRail,
    )
}

@Stable
class AppState(
    private val navController: NavController,
    private val viewModel: AppViewModel,
    val shouldUseNavRail: Boolean,
) {
    val currentDestination: NavDestination?
        @Composable
        get() = navController
            .currentBackStackEntryAsState()
            .value
            ?.destination

    val currentTopLevelDestination: String?
        @Composable
        get() = when {
            currentDestination?.hasRoute<Posts>() == true -> "Posts"
            currentDestination?.hasRoute<Drafts>() == true -> "Drafts"
            currentDestination?.hasRoute<Analytics>() == true -> "Analytics"
            currentDestination?.hasRoute<Settings>() == true -> "Settings"
            else -> null
        }
}

@Composable
@Preview
fun App(
    viewModel: AppViewModel = koinViewModel<AppViewModel>(),
    navController: NavHostController = rememberNavController(),
    shouldUseNavRail: Boolean,
    appState: AppState = rememberAppState(
        navController = navController,
        viewModel = viewModel,
        shouldUseNavRail = shouldUseNavRail,
    ),
    onAppReady: () -> Unit = {},
    onNetChange: (Boolean) -> Unit = {},
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
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

data class SMShareBottomAppBarState(
    val configure: @Composable () -> Unit,
)

data class BottomSheetState(
    val content: (@Composable () -> Unit)? = null,
)

data class SMShareTopAppBarState(
    val configure: (@Composable () -> Unit)? = null,
)

@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
@Suppress("CyclomaticComplexMethod")
fun Home(
    appState: AppState,
    onNetChange: (Boolean) -> Unit = {},
    viewModel: AppViewModel,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onAppReady: () -> Unit = {},
    onShowSnackbar: suspend (message: String, actions: String?, success: Boolean) -> Boolean = { msg, act, isSuccess ->
        snackbarHostState.showSnackbar(
            message = msg,
            actionLabel = act,
            duration = SnackbarDuration.Short,
        ) == SnackbarResult.ActionPerformed
    },
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentOnAppReady by rememberUpdatedState(onAppReady)

    val appUiState by viewModel.userData.collectAsStateWithLifecycle()
    val isOnline by viewModel.isOnLine.collectAsStateWithLifecycle()
    val accounts by viewModel.accountsAndProfile.collectAsStateWithLifecycle()
    val currentAccountAndProfile by viewModel.currentAccount.collectAsStateWithLifecycle()
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

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

    val onMenuItemClick: (bottomNavItem: BottomNavItem) -> Unit = { navItem ->
        val navOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            navController.graph.findStartDestination().route?.let {
                popUpTo(it) {
                    saveState = true
                }
            }
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }

        navItem.navigate(navController, navOptions)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val title = when {
        currentDestination?.hasRoute<Posts>() == true -> stringResource(
            Res.string.main_nav_title_posts,
        )

        currentDestination?.hasRoute<Drafts>() == true -> stringResource(
            Res.string.main_nav_title_drafts,
        )

        currentDestination?.hasRoute<Analytics>() == true -> stringResource(
            Res.string.main_nav_title_analytics,
        )

        currentDestination?.hasRoute<Settings>() == true -> stringResource(
            Res.string.main_nav_title_settings,
        )
        else -> ""
    }

    val mainTopAppBar: @Composable (() -> Unit) = {
        CenterAlignedTopAppBar(
            title = {
                var expanded by remember { mutableStateOf(false) }
                val scrollState = rememberScrollState()

                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                    if (currentAccountAndProfile != null) {
                        Surface(onClick = { expanded = true }, shape = CircleShape) {
                            val painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalPlatformContext.current)
                                    .data(currentAccountAndProfile?.profile?.picture)
                                    .error { it.placeholder() }
                                    .build(),
                                placeholder = painterResource(Res.drawable.avatar6),
                                contentScale = ContentScale.Crop,
                            )
                            val account = currentAccountAndProfile?.account
                            ChannelWithName(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(vertical = 8.dp, horizontal = 16.dp),
                                name = account?.name ?: "Unknown",
                                avatarSize = 38.dp,
                                avatar = painter,
                                indicator = iconIndicatorForAccountType(account?.type),
                            )
                        }

                        if (accounts.size > 1) {
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                accounts.forEach { (account, profile) ->
                                    DropdownMenuItem(
                                        text = {
                                            val painter = rememberAsyncImagePainter(
                                                model = ImageRequest.Builder(LocalPlatformContext.current)
                                                    .data(profile.picture)
                                                    .error { it.placeholder() }
                                                    .build(),
                                                placeholder = painterResource(Res.drawable.avatar6),
                                                contentScale = ContentScale.Crop,
                                            )

                                            ChannelWithName(
                                                modifier = Modifier.padding(
                                                    horizontal = 8.dp,
                                                    vertical = 4.dp,
                                                ),
                                                name = account.name,
                                                avatarSize = 38.dp,
                                                avatar = painter,
                                                indicator = iconIndicatorForAccountType(
                                                    account.type,
                                                ),
                                            )
                                        },
                                        onClick = { expanded = false },
                                    )
                                }
                            }
                            LaunchedEffect(expanded) {
                                if (expanded) {
                                    // Scroll to show the bottom menu items.
                                    scrollState.scrollTo(scrollState.maxValue)
                                }
                            }
                        }
                    } else {
                        Text(text = title)
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

    val hapticFeedback = LocalHapticFeedback.current
    val mainBottomNavigation: @Composable () -> Unit = {
        NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
            listOf(
                BottomNavItem.PostsNavItem(),
                BottomNavItem.DraftsBottomNavItem(),
                BottomNavItem.AnalyticsBottomNavItem(),
                BottomNavItem.SettingsBottomNavItem(),
            ).fastForEach { navItem ->
                NavigationBarItem(
                    selected = navItem.isSelected(navController),
                    onClick = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        onMenuItemClick(navItem)
                    },
                    icon = navItem.icon,
                    label = { Text(text = navItem.title()) },
                )
            }
        }
    }

    var showAddConnectionBottomSheet by remember { mutableStateOf(false) }
    var topAppBarState by remember { mutableStateOf<SMShareTopAppBarState?>(null) }
    var bottomAppBarState by remember { mutableStateOf<SMShareBottomAppBarState?>(null) }
    val sheetState = rememberModalBottomSheetState(false)
    val channels by viewModel.channels.collectAsStateWithLifecycle()
    val onCloseSidNav: (SideNavMenuAction?) -> Unit = { sideNavMenAction ->
        scope
            .launch { drawerState.close() }
            .invokeOnCompletion {
                when (sideNavMenAction) {
                    SideNavMenuAction.Logout -> {
                        viewModel.logout()
                        val popUpToRout =
                            navController.graph.startDestinationRoute!!
                        navController.navigateToSignIn(
                            navOptions {
                                popUpTo(popUpToRout) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            },
                        )
                    }

                    SideNavMenuAction.AddNewConnection ->
                        scope
                            .launch { sheetState.show() }
                            .invokeOnCompletion {
                                if (channels.isEmpty()) {
                                    scope.launch {
                                        onShowSnackbar(
                                            "All caught up, no more channels to add",
                                            "Ok",
                                            false
                                        )
                                    }
                                } else {
                                    showAddConnectionBottomSheet = true
                                }
                            }

                    SideNavMenuAction.ManageAccounts -> navController.navigateToManageAccounts()

                    SideNavMenuAction.ManageTags -> navController.navigateToTags()
                    
                    null -> {}
                }
            }
    }

    startDestination?.let { navHostStarDestination ->
        Box(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                ModalNavigationDrawer(
                    modifier = Modifier.fillMaxSize().background(Color.Red),
                    drawerState = drawerState,
                    drawerContent = drawerContent(accounts, onCloseSidNav),
                    gesturesEnabled = appState.currentTopLevelDestination != null,
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            snackbarHost = { SnackbarHost(snackbarHostState) },
                            topBar = {
                                if (isLoggedIn) {
                                    topAppBarState?.configure?.invoke()
                                }
                            },
                            floatingActionButton = {
                                AnimatedVisibility(appState.currentTopLevelDestination != null) {
                                    ExtendedFloatingActionButton(onClick = {
                                        if (accounts.isEmpty() && currentAccountAndProfile == null) {
                                            scope.launch {
                                                onShowSnackbar(
                                                    "No account added, please add account to continue",
                                                    "",
                                                    false,
                                                )
                                            }
                                        } else {
                                            navController.navigateToCompose(
                                                currentAccountAndProfile?.account?.id
                                            )
                                        }
                                    }) {
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
                                if (!appState.shouldUseNavRail) {
                                    bottomAppBarState?.configure?.invoke()
                                }
                            },
                            content = { innerPadding ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
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
                                    if (isLoading) {
                                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                                    }

                                    Row(modifier = Modifier.fillMaxSize()) {
                                        if (appState.shouldUseNavRail) {
                                            NavigationRail(modifier = Modifier.fillMaxHeight()) {
                                                listOf(
                                                    BottomNavItem.PostsNavItem(),
                                                    BottomNavItem.DraftsBottomNavItem(),
                                                    BottomNavItem.AnalyticsBottomNavItem(),
                                                    BottomNavItem.SettingsBottomNavItem(),
                                                ).fastForEach { navItem ->
                                                    NavigationRailItem(
                                                        modifier = Modifier.padding(
                                                            vertical = 16.dp,
                                                        ),
                                                        selected = navItem.isSelected(
                                                            navController,
                                                        ),
                                                        onClick = { onMenuItemClick(navItem) },
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
                                            startDestination = navHostStarDestination,
                                        ) {
                                            // auth
                                            installAuth<Auth>(
                                                onSetupTopAppBar = onSetupTopAppBar,
                                                onLoginClick = { navController.popBackStack() },
                                                onSignUpComplete = {
                                                    navController.navigateToPosts(
                                                        navOptions {
                                                            popUpTo(AuthDestinations.SignIn) {
                                                                inclusive = true
                                                            }
                                                            launchSingleTop = true
                                                        },
                                                    )
                                                },
                                                onCreateAccountClick = {
                                                    navController
                                                        .navigateToSignUp()
                                                },
                                                onLoginComplete = {
                                                    navController.navigateToPosts(
                                                        navOptions = navOptions {
                                                            popUpTo<Auth> {
                                                                inclusive = true
                                                            }
                                                            launchSingleTop = true
                                                        },
                                                    )
                                                },
                                                onShowSnackbar = onShowSnackbar,
                                                onSetUpBottomAppBar = { bottomAppBarState = it },
                                            )

                                            // Posts
                                            installPosts(
                                                onSetupTopAppBar = {
                                                    topAppBarState =
                                                        SMShareTopAppBarState(
                                                            configure = mainTopAppBar,
                                                        )
                                                },
                                                onSetUpBottomAppBar = {
                                                    bottomAppBarState =
                                                        SMShareBottomAppBarState(
                                                            configure = mainBottomNavigation,
                                                        )
                                                },
                                            )

                                            // Drafts
                                            draftsScreen(
                                                onSetupTopAppBar = {
                                                    topAppBarState =
                                                        SMShareTopAppBarState(
                                                            configure = mainTopAppBar,
                                                        )
                                                },
                                                onSetUpBottomAppBar = {
                                                    bottomAppBarState =
                                                        SMShareBottomAppBarState(
                                                            configure = mainBottomNavigation,
                                                        )
                                                },
                                            )

                                            // Analytics
                                            analyticsScreen(
                                                onSetupTopAppBar = {
                                                    topAppBarState =
                                                        SMShareTopAppBarState(
                                                            configure = mainTopAppBar,
                                                        )
                                                },
                                                onSetUpBottomAppBar = {
                                                    bottomAppBarState =
                                                        SMShareBottomAppBarState(
                                                            configure = mainBottomNavigation,
                                                        )
                                                },
                                            )

                                            // Settings
                                            settingsScreen(
                                                onSetupTopAppBar = {
                                                    topAppBarState =
                                                        SMShareTopAppBarState(
                                                            configure = mainTopAppBar,
                                                        )
                                                },
                                                onSetUpBottomAppBar = {
                                                    bottomAppBarState =
                                                        SMShareBottomAppBarState(
                                                            configure = mainBottomNavigation,
                                                        )
                                                },
                                            )

                                            // Compose a message
                                            composeMessageScreen(
                                                onSetupTopAppBar = onSetupTopAppBar,
                                                onShowSnackbar = onShowSnackbar,
                                                onSetUpBottomAppBar = { bottomAppBarState = it },
                                                onCancel = { navController.popBackStack() },
                                            )

                                            //accounts
                                            installAccountsScreen(
                                                onSetupTopAppBar = { topAppBarState = it },
                                                onSetUpBottomAppBar = { bottomAppBarState = it },
                                                onBackClick = { navController.popBackStack() },
                                                onShowSnackbar = onShowSnackbar,
                                            )

                                            // tags
                                            tagsScreen(
                                                onSetupTopAppBar = { topAppBarState = it },
                                                onSetUpBottomAppBar = { bottomAppBarState = it },
                                                onBackClick = { navController.popBackStack() },
                                            )
                                        }
                                    }
                                }
                            }
                        )

                        val onChannelClick: (ChannelConfig) -> Unit = { channel ->
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                showAddConnectionBottomSheet = false
                                viewModel.authoriseChannel(channel)
                            }
                        }

                        if (showAddConnectionBottomSheet) {
                            AddNewConnectionChannel(channels, onChannelClick, sheetState) {
                                showAddConnectionBottomSheet = false
                            }
                        }
                    }
                }
            }

            // show red status bar when offline and is mobile
            val isMobilePlatform = when (val state = appUiState) {
                AppUiState.Loading -> false
                is AppUiState.Success ->
                    state.platform.type == SupportedPlatformType.Android ||
                        state.platform.type == SupportedPlatformType.iOS
            }
            AnimatedVisibility(!isOnline && isMobilePlatform) {
                Box(modifier = Modifier.fillMaxWidth().height(48.dp).background(Color.Red))
            }
        }
    }

    LaunchedEffect(startDestination) {
        if (startDestination != null) {
            currentOnAppReady()
        }
    }

    // The effect is produced only once, as `Unit` never changes
    DisposableEffect(Unit) {
        // Sets up the listener to call `NavController.navigate()`
        // for the composable that has a matching `navDeepLink` listed
        ExternalUriHandler.listener = { uri ->
            val url = Url(uri)

            if (url.encodedPath.contains("/smshare/auth/callback")) {
                val code = url.parameters["code"] ?: ""
                val state = url.parameters["state"] ?: ""

                if (code.isNotBlank() || state.isNotBlank()) {
                    viewModel.exchangeCodeForAccessToken(code, state)
                }
            }
        }
        // Removes the listener when the composable is no longer active
        onDispose {
            ExternalUriHandler.listener = null
        }
    }
}
