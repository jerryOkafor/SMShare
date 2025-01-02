plugins {
    alias(libs.plugins.smshare.android.library)
    alias(libs.plugins.smshare.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
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
