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
            api(libs.androidx.datastore.core.okio)
            api(libs.androidx.datastore.preferences.core)
            implementation(libs.squareup.okio)

            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.jerryokafor.core.datastore"
}
