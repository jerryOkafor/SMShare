package com.jerryokafor.smshare

import App
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jerryokafor.smshare.injection.desktopModule
import injection.initKoin

private val koin =
    initKoin {
        modules(desktopModule())
    }.koin

fun main() =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "SM Share",
        ) {
            App()
        }
    }
