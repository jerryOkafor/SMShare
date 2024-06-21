plugins {
    id("com.jerryokafor.smshare.android.library")
    id("com.jerryokafor.smshare.multiplatform")
    id("com.jerryokafor.smshare.android.detekt")
    id("com.jerryokafor.smshare.android.ktlint")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare.core.model"
}
