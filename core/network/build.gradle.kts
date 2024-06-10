plugins {
    id("com.jerryokafor.smshare.android.library")
    id("com.jerryokafor.smshare.multiplatform")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)

            api(libs.ktor.client.core)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.serialization.kotlinx.json)

            implementation(libs.koin.core)

            implementation(libs.dev.whyoleg.cryptography.random)
            implementation(libs.dev.whyoleg.cryptography.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.dev.whyoleg.cryptography.provider.jdk)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.dev.whyoleg.cryptography.provider.apple)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.netty)
            implementation(libs.dev.whyoleg.cryptography.provider.jdk)
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare.core.network"
}
