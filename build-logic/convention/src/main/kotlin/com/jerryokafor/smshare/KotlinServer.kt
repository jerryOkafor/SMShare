package com.jerryokafor.smshare

import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureKotlinServer() {
    extensions.configure<KotlinJvmProjectExtension> {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(JVMLanguageVersion))
        }
        sourceSets.all {}
        
        compilerOptions {
            freeCompilerArgs.add("-Xskip-prerelease-check")
        }
    }

    // Configure Java to use our chosen language level. Kotlin will automatically pick this up
    configureJava()
}

