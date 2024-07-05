package com.jerryokafor.smshare

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            applyDefaultHierarchyTemplate()

            if (pluginManager.hasPlugin("com.android.library") ||
                pluginManager.hasPlugin("com.android.application")
            ) {
                androidTarget()
            }

            iosX64()
            iosArm64()
            iosSimulatorArm64()

            jvm("desktop")

            targets.withType<KotlinNativeTarget>().configureEach {
                binaries.configureEach {
                    // Add linker flag for SQLite. See:
                    // https://github.com/touchlab/SQLiter/issues/77
                    linkerOpts("-lsqlite3")

                    // Workaround for https://youtrack.jetbrains.com/issue/KT-64508
                    freeCompilerArgs +=
                        "-Xdisable-phases=RemoveRedundantCallsToStaticInitializersPhase"
                }

                configureKotlinKMP()
            }

            configureArgs()

            configureKotlin()
        }
    }
}
