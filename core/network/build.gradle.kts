import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode

plugins {
    kotlin("native.cocoapods")
    id("com.jerryokafor.smshare.android.library")
    id("com.jerryokafor.smshare.multiplatform")
    id("com.jerryokafor.smshare.android.detekt")
    id("com.jerryokafor.smshare.android.ktlint")
    alias(libs.plugins.kotlinx.serialization)
}

group = "com.jerryokafor.smshare"
version = "1.0.0"

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.dev.whyoleg.cryptography.provider.jdk)

            implementation(libs.androidx.core.ktx)
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)

            api(libs.ktor.client.core)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.serialization.kotlinx.json)

            implementation(libs.koin.core)

            implementation(libs.dev.whyoleg.cryptography.random)
            implementation(libs.dev.whyoleg.cryptography.core)
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

    cocoapods {
        summary = "SM Share Core Network  - part of SM Share Project"
        homepage = "https://github.com/jerryOkafor/SMShare"
        ios.deploymentTarget = libs.versions.iosDeploymentVersion.get()
        license = "MIT"

        name = "CoreNetwork"

        framework {
            baseName = "CoreNetwork"
            isStatic = false
            transitiveExport = false
            embedBitcode(BitcodeEmbeddingMode.BITCODE)
        }

//        extraSpecAttributes["libraries"] = "'c++', 'sqlite3'"
//        extraSpecAttributes.put("swift_version", "\"5.0\"")

        // https://github.com/tonymillion/Reachability
        pod("Reachability") {
            version = libs.versions.reachability.get()
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare.core.network"

    // Include our manifest with required permission for merger
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}
