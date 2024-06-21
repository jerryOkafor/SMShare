plugins {
    `kotlin-dsl`
}

group = "com.jerryokafor.smshare.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        plugins {
            register("kotlinMultiplatform") {
                id = "com.jerryokafor.smshare.multiplatform"
                implementationClass = "com.jerryokafor.smshare.KotlinMultiplatformConventionPlugin"
            }

            register("kotlinAndroid") {
                id = "com.jerryokafor.smshare.kotlin.android"
                implementationClass = "com.jerryokafor.smshare.KotlinAndroidConventionPlugin"
            }

            register("androidLibrary") {
                id = "com.jerryokafor.smshare.android.library"
                implementationClass = "com.jerryokafor.smshare.AndroidLibraryConventionPlugin"
            }

            register("androidApplication") {
                id = "com.jerryokafor.smshare.android.application"
                implementationClass = "com.jerryokafor.smshare.AndroidApplicationConventionPlugin"
            }

            register("detekt") {
                id = "com.jerryokafor.smshare.android.detekt"
                implementationClass = "com.jerryokafor.smshare.DetektConventionPlugin"
            }

            register("ktlint") {
                id = "com.jerryokafor.smshare.android.ktlint"
                implementationClass = "com.jerryokafor.smshare.KtLintConventionPlugin"
            }
        }
    }
}

// More info: https://vaibhav3011.medium.com/effortless-multimodule-configuration-for-kotlin-multiplatform-projects-with-gradle-convention-8e6593dff1d9
