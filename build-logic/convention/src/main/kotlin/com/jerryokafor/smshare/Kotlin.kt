package com.jerryokafor.smshare

import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

const val JVMLanguageVersion = 21
val JVMTarget = JvmTarget.JVM_21

fun Project.configureKotlin() {
    extensions.configure<KotlinMultiplatformExtension> {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(JVMLanguageVersion))
        }
        sourceSets.all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
//            languageSettings.enableLanguageFeature("ExplicitBackingFields")
        }
    }
    
    // Configure Java to use our chosen language level. Kotlin will automatically pick this up
    configureJava()
}

