import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    id("com.jerryokafor.smshare.android.application")
    id("com.jerryokafor.smshare.android.detekt")
    id("com.jerryokafor.smshare.android.ktlint")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
//    alias(libs.plugins.kotlinx.rpc.platform)
}

kotlin {
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
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)

            dependencies {
                implementation("androidx.compose.ui:ui-test-junit4-android:1.6.8")
                debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.8")
            }
        }
    }

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

    sourceSets {

        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }

        val desktopMain by getting
        val desktopTest by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.core.database)
            implementation(projects.core.domain)
            implementation(projects.core.datastore)
            implementation(projects.core.config)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.dev.chrisbanes.windowSizeClass)

            // ViewModel
            implementation(libs.androidx.lifecycle.viewmodel)

//            implementation(libs.kotlinx.rpc.runtime.client)
//            implementation(libs.kotlinx.rpc.runtime.serialization.json)
//            implementation(libs.kotlinx.rpc.transport.ktor.client)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.websockets)

            implementation(libs.navigation.compose)

            implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc05")
            implementation("com.github.skydoves:flexible-bottomsheet-material3:0.1.3")

            api(libs.koin.core)
            api(libs.koin.test)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel.kmp)
        }

        commonTest.dependencies {
//            implementation(libs.kotlin.test)

            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.commons.codec)
            api(libs.kotlinx.coroutines.swingx)
            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.netty)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerPlugin.get()
    }
    dependencies {
        implementation(libs.androidx.core.splashscreen)
        implementation(libs.androidx.activity.ktx)
        implementation(libs.androidx.browser)

//        testImplementation(libs.junit)
//
//        androidTestImplementation(libs.androidx.test.junit)
//        androidTestImplementation(libs.androidx.espresso.core)
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
