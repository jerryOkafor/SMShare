plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.rpc)
    alias(libs.plugins.kotlinx.serialization)
    application

    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
}

group = "com.jerryokafor.smshare"
version = "1.0.0"
application {
    mainClass.set("com.jerryokafor.smshare.ApplicationKt")
    applicationDefaultJvmArgs =
        listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.core.rpc)
    implementation(projects.core.model)

    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.cors.jvm)
    implementation(libs.ktor.server.websockets.jvm)
    implementation(libs.ktor.server.host.common.jvm)

    // Koin
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    // Kotlinx RPC
    implementation(libs.kotlinx.rpc.server)
    implementation(libs.kotlinx.rpc.serialization.json)
    implementation(libs.kotlinx.rpc.transport.ktor.server)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlinx.rpc.client)
    testImplementation(libs.kotlinx.rpc.transport.ktor.client)
    testImplementation(libs.kotlin.test.junit)
}
