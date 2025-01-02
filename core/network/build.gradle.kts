import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode

plugins {
    kotlin("native.cocoapods")
    alias(libs.plugins.smshare.android.library)
    alias(libs.plugins.smshare.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.appoloGraphql)
    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
}

group = "com.jerryokafor.smshare"
version = "1.0.0"

kotlin {
    sourceSets {
        val desktopMain by getting

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

            //Apollo GraphQL
            api("com.apollographql.apollo:apollo-runtime:4.0.0")
            api("com.apollographql.adapters:apollo-adapters-kotlinx-datetime:0.0.4")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.dev.whyoleg.cryptography.provider.apple)
        }

        desktopMain.dependencies {
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
            headers = "Reachability-umbrella.h"
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare.core.network"

    // Include our manifest with required permission for merger
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}



apollo {
    service("service") {
        packageName.set("com.jerryokafor.smshare.graphql")
        codegenModels.set("operationBased")
        generateDataBuilders.set(true)
        generateFragmentImplementations.set(true)
        generateSchema.set(true)

        mapScalar(
            "LocalDateTime",
            "kotlinx.datetime.LocalDateTime",
            "com.apollographql.adapter.datetime.KotlinxLocalDateTimeAdapter"
        )

        mapScalar(
            "LocalDate",
            "kotlinx.datetime.LocalDate",
            "com.apollographql.adapter.datetime.KotlinxLocalDateAdapter"
        )

        introspection {
            endpointUrl.set("http://localhost:8080/graphql")
            schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
        }
    }
}
