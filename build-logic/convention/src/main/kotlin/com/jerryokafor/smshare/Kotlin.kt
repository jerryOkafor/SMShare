package com.jerryokafor.smshare

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureKotlin() {
    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
//            languageSettings.enableLanguageFeature("ExplicitBackingFields")
        }
    }
    // Configure Java to use our chosen language level. Kotlin will automatically pick this up
    configureJava()
}
