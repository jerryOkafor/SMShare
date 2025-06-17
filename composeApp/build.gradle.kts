import org.gradle.kotlin.dsl.implementation
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.smshare.kotlin.multiplatform)
    alias(libs.plugins.smshare.android.application)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlinx.rpc)
    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
    alias(libs.plugins.jetbrains.compose.hotReload)
}

kotlin {
    applyDefaultHierarchyTemplate()

//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(project.projectDir.path)
//                    }
//                }
//            }
//        }
//        binaries.executable()
//    }

    // Run instrumented (emulator) tests for Android
    androidTarget {}

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    composeCompiler {
        featureFlags = setOf(ComposeFeatureFlag.StrongSkipping)
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
//            languageSettings.enableLanguageFeature("ExplicitBackingFields")
        }
        val desktopMain by getting
        val desktopTest by getting

        @Suppress("UnusedPrivateProperty")
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.androidx.test.junit)
                implementation(libs.robolectric)

                implementation(libs.androidx.ui.test.junit4.android)
                implementation(libs.androidx.ui.test.manifest)

                implementation("androidx.fragment:fragment-testing:1.8.5")
            }
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)

            // Util
            implementation(libs.apache.commons.codec)
        }

        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.database)
            implementation(projects.core.domain)
            implementation(projects.core.datastore)
            implementation(projects.core.config)
            implementation(projects.core.network)
            implementation(projects.core.rpc)

            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(
                "org.jetbrains.compose.material3.adaptive:adaptive-navigation:1.0.0-alpha03",
            ) {
                exclude(group = "org.jetbrains.androidx.window")
            }
            implementation("org.jetbrains.compose.material3.adaptive:adaptive:1.0.0-alpha03") {
                exclude(group = "org.jetbrains.androidx.window")
            }
            implementation(
                "org.jetbrains.compose.material3.adaptive:adaptive-layout:1.0.0-alpha03",
            ) {
                exclude(group = "org.jetbrains.androidx.window")
            }
            implementation(compose.material3AdaptiveNavigationSuite) {
                exclude(group = "org.jetbrains.androidx.window")
            }
            implementation(libs.jetbrains.compose.material3.windowSizeClass)

            implementation(libs.kotlinx.datetime)
            implementation(libs.jetbrains.kotlinx.html)

            // ViewModel
            implementation(libs.jetbrains.androidx.viewmodel.compose)
            implementation(libs.jetbrains.androidx.lifecycle.runtime.compose)
            implementation(libs.jetbrains.androidx.savedstate)

            // RPC
            implementation(libs.kotlinx.rpc.client)
            implementation(libs.kotlinx.rpc.serialization.json)
            implementation(libs.kotlinx.rpc.transport.ktor.client)

            // Ktor Client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.websockets)

            // Navigation
            implementation(libs.navigation.compose)
            
            // Utils
            implementation(libs.richeditor.compose)
            implementation(libs.flexible.bottomsheet.material3)

            // Koin - DI
            api(libs.koin.core)
            api(libs.koin.test)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
        }

        commonTest.dependencies {
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlin.test)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.apache.commons.codec)
            api(libs.kotlinx.coroutines.swingx)

            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.netty)
            implementation(libs.ktor.server.html.builder)

            implementation("androidx.window:window-core-jvm:1.4.0")
        }

        desktopTest.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.jerryokafor.smshare"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }

    dependencies {
        implementation(libs.androidx.core.splashscreen)
        implementation(libs.androidx.activity.ktx)
        implementation(libs.androidx.browser)

        testImplementation(libs.junit)

        androidTestImplementation(libs.androidx.test.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.jerryokafor.smshare"
            packageVersion = "1.0.0"
        }
    }
}
