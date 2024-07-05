package com.jerryokafor.smshare

import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.HasUnitTestBuilder
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroid() {
    android {
        compileSdkVersion(
            libs
                .findVersion("android-compileSdk")
                .get()
                .toString()
                .toInt(),
        )

        defaultConfig {
            minSdk = libs
                .findVersion("android-minSdk")
                .get()
                .toString()
                .toInt()

            targetSdk = libs
                .findVersion("android-targetSdk")
                .get()
                .toString()
                .toInt()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            manifestPlaceholders += mapOf("appAuthRedirectScheme" to "empty")
        }

        compileOptions {
            // https://developer.android.com/studio/write/java8-support
            isCoreLibraryDesugaringEnabled = true
        }

        testOptions {
            if (this@android is LibraryExtension) {
                // We only want to configure this for library modules
                targetSdk = libs
                    .findVersion("android-targetSdk")
                    .get()
                    .toString()
                    .toInt()
            }

            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }
        }
    }

    androidComponents {
        beforeVariants(selector().withBuildType("release")) { variantBuilder ->
            (variantBuilder as? HasUnitTestBuilder)?.apply {
                enableUnitTest = false
            }
        }
    }

    dependencies {
        // https://developer.android.com/studio/write/java8-support
        "coreLibraryDesugaring"(libs.findLibrary("tools.desugarjdklibs").get())
    }
}

fun Project.android(action: BaseExtension.() -> Unit) = extensions.configure<BaseExtension>(action)

fun Project.androidComponents(action: AndroidComponentsExtension<*, *, *>.() -> Unit) {
    extensions.configure(AndroidComponentsExtension::class.java, action)
}
