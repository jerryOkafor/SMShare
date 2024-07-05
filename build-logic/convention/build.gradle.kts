plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlinJvm)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

group = "com.jerryokafor.smshare.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.gradlePlugin.api)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.firebase.testLab.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)
    implementation(gradleKotlinDsl())
}

gradlePlugin {
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
            id = "com.jerryokafor.smshare.detekt"
            implementationClass = "com.jerryokafor.smshare.DetektConventionPlugin"
        }

        register("ktlint") {
            id = "com.jerryokafor.smshare.ktlint"
            implementationClass = "com.jerryokafor.smshare.KtLintConventionPlugin"
        }

        register("firebaseTestLab") {
            id = "com.jerryokafor.smshare.firebase.testLab"
            implementationClass = "com.jerryokafor.smshare.FirebaseTestLabConvention"
        }
        register("testCoverage") {
            id = "com.jerryokafor.smshare.coverage"
            implementationClass = "com.jerryokafor.smshare.CoverageConventionPlugin"
        }
    }
}

// More info: https://vaibhav3011.medium.com/effortless-multimodule-configuration-for-kotlin-multiplatform-projects-with-gradle-convention-8e6593dff1d9
