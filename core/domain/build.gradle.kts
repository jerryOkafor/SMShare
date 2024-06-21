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
            api(projects.core.model)
            api(projects.core.network)

            api(libs.kotlinx.coroutines.core)
            api(libs.co.touchlab.kermit)

            implementation(projects.core.datastore)
            implementation(projects.core.database)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare.core.domain"
}
