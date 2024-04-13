@file:Suppress("OPT_IN_USAGE")

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)
}

compose {
    kotlinCompilerPlugin.set(libs.versions.kotlinCompilerPlugin.get())
}
kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//            }
//        }
//        binaries.executable()
//    }

    //K2 compiler is enabled by default for Kotlin 2.0.0-RC1.
    //No support yet for KMP so we force kotlin 1.9
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_1_9)
        apiVersion.set(KotlinVersion.KOTLIN_1_9)

//        freeCompilerArgs.add("")
    }

    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.dev.chrisbanes.windowSizeClass)

            api(libs.koin.core)
            api(libs.koin.test)
            api(libs.koin.compose)

            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)
            api(libs.ktor.client.core)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.serialization.kotlinx.json)

            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)

            implementation(libs.dev.whyoleg.cryptography.random)
            implementation(libs.dev.whyoleg.cryptography.core)

            implementation(libs.annotation.internal.annotation)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)

            api(libs.kotlinx.coroutines.android)

            implementation(libs.koin.android)
            implementation(libs.dev.whyoleg.cryptography.provider.jdk)
        }


        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.stately.common)
            implementation(libs.dev.whyoleg.cryptography.provider.apple)


            implementation(libs.annotation.internal.annotation)
        }

        iosTest.dependencies {
            implementation(libs.kotlin.test)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.commons.codec)

            api(libs.kotlinx.coroutines.swingx)

            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.netty)
            implementation(libs.dev.whyoleg.cryptography.provider.jdk)
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.jerryokafor.smshare"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation(libs.androidx.ui.tooling.preview.android)
        testImplementation(libs.junit)

        implementation(libs.androidx.browser)

        androidTestImplementation(libs.androidx.test.junit)
        androidTestImplementation(libs.androidx.junit.ktx)
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

compose.experimental {
    web.application {}
}