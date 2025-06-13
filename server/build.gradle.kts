import com.jerryokafor.smshare.configureKotlinServer
import com.jerryokafor.smshare.configurePowerAssert
import io.ktor.plugin.features.DockerPortMapping
import io.ktor.plugin.features.DockerPortMappingProtocol
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.rpc)
    alias(libs.plugins.kotlinx.serialization)
    application

    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
    alias(libs.plugins.flywaydb)
    alias(libs.plugins.expediagroup.graphql)
    alias(libs.plugins.kotlin.powerAssert)
}

description = "SMS Sahre Server"
group = "com.jerryokafor.smshare"
version = "1.0.0"

application {
    mainClass.set("com.jerryokafor.smshare.ApplicationKt")
    applicationDefaultJvmArgs =
        listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

configureKotlinServer()

configurePowerAssert()

dependencies {
    implementation(projects.core.rpc)
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.core.jvm)

    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.cors.jvm)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.websockets.jvm)
    implementation(libs.ktor.server.host.common.jvm)
    implementation(libs.ktor.network.tls.certificates)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    // Kotlinx RPC
    implementation(libs.kotlinx.rpc.server)
    implementation(libs.kotlinx.rpc.serialization.json)
    implementation(libs.kotlinx.rpc.transport.ktor.server)
    testImplementation(libs.kotlinx.rpc.client)
    testImplementation(libs.kotlinx.rpc.transport.ktor.client)

    // Graph QL
    implementation(libs.expediagroup.graphql.kotlin.ktor.server)

    // Exposed
    implementation(libs.jetbrains.exposed.core)
    implementation(libs.jetbrains.exposed.jdbc)
    implementation(libs.jetbrains.exposed.dao)

    // Utils
    implementation(libs.postgres.postgresql)
    implementation(libs.zaxxer.hikaricp)
    implementation(libs.h2database.h2)
    implementation(libs.flywaydb.flyway.core)
    implementation(libs.mindrot.jbcrypt)
    implementation(libs.sendgrid.java)
    implementation(libs.firebase.firestore.ktx)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlinx.coroutines.test)
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_19)
        localImageName.set("smsshare-backend")
        imageTag.set("0.0.1-preview")

        portMappings.set(
            listOf(
                DockerPortMapping(
                    outsideDocker = 80,
                    insideDocker = 8080,
                    protocol = DockerPortMappingProtocol.TCP,
                ),
            ),
        )

//        externalRegistry.set(
//            dockerHub(
//                appName = provider { "ktor-app" },
//                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
//                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
//            )
//        )
    }
}

flyway {
    url = System.getenv("DB_URL")
    user = System.getenv("DB_USER")
    password = System.getenv("DB_PASSWORD")
    baselineOnMigrate = true
}

graphql {
    schema {
        packages = listOf("com.jerryokafor.smshare")
    }
}
