package com.jerryokafor.smshare

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompileCommon

fun KotlinNativeTarget.configureKotlinKMP() {
    compilations.configureEach {
        compileTaskProvider.configure {
            compilerOptions {
                // Various opt-ins
                freeCompilerArgs.addAll(
                    "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                    "-opt-in=kotlinx.cinterop.BetaInteropApi",
                )
            }
        }
    }
}

fun KotlinMultiplatformExtension.configureArgs() {
    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.configureMetaData() {
    metadata {
        compilations.configureEach {
            if (name == KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME) {
                compileTaskProvider.configure {
                    // We replace the default library names with something more unique (the project path).
                    // This allows us to avoid the annoying issue of `duplicate library name: foo_commonMain`
                    // https://youtrack.jetbrains.com/issue/KT-57914
                    val projectPath = path.substring(1).replace(":", "_")
                    this as KotlinCompileCommon
                    moduleName.set("${projectPath}_commonMain")
                }
            }
        }
    }
}
