plugins {
    id("com.jerryokafor.smshare.android.library")
    id("com.jerryokafor.smshare.multiplatform")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinx.rpc)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlinx.rpc.runtime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare.core.rpc"
}
