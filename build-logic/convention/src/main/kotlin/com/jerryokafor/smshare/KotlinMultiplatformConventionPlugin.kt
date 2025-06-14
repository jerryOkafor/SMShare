package com.jerryokafor.smshare

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
   
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("org.jetbrains.kotlin.plugin.power-assert")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            applyDefaultHierarchyTemplate()

            if (pluginManager.hasPlugin("com.android.library") ||
                pluginManager.hasPlugin("com.android.application")
            ) {
                androidTarget {
                    @OptIn(ExperimentalKotlinGradlePluginApi::class)
                    compilerOptions {
                        jvmTarget.set(JVMTarget)
                    }
                }
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

        configurePowerAssert()
    }
}
