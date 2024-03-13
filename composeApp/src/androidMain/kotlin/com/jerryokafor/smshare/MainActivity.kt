package com.jerryokafor.smshare

import App
import AuthManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope


class MainActivity : ComponentActivity(), AndroidScopeComponent {
    override val scope: Scope by activityScope()

    private val authManager: AuthManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authManager.bindContext(this)

        enableEdgeToEdge()

        setContent {
            val isDarkTheme = isSystemInDarkTheme()

            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                    ) { resources -> isDarkTheme },

                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = lightScrim,
                        darkScrim = darkScrim,
                    ) { resources -> isDarkTheme },
                )
                onDispose {}
            }

            App(isDarkTheme = isDarkTheme)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val url = intent?.data
        when (url?.path) {
            "/auth/callback" -> {
                val code = url.getQueryParameter("code")!!
                val state = url.getQueryParameter("state")

                Log.d("MainActivity", "Received code: $code, state: $state")

                lifecycleScope.launch {
                    val tokenResponse = authManager.currentChannel?.getToken(
                        code = code,
                        redirectUrl = "http://com.jerryokafor.smshare/auth/callback"
                    )

                    Log.d("MainActivity", "Received token: $tokenResponse")
                }
            }

            else -> {
                Log.d("MainActivity", "Unhandled: $url")
            }
        }

        Log.d("MainActivity", "Received URL: $url")
    }
}

@Suppress("MagicNumber")
private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

@Suppress("MagicNumber")
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
