plugins {
    id("com.jerryokafor.smshare.android.detekt")
    id("com.jerryokafor.smshare.android.ktlint")
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
//    alias(libs.plugins.kotlinx.rpc.platform)
    alias(libs.plugins.kotlinx.serialization)
    application
}

group = "com.jerryokafor.smshare"
version = "1.0.0"
application {
    mainClass.set("com.jerryokafor.smshare.ApplicationKt")
    applicationDefaultJvmArgs =
        listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
//    implementation(projects.core.rpc)
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
//    implementation(libs.kotlinx.rpc.runtime.server)
//    implementation(libs.kotlinx.rpc.runtime.serialization.json)
//    implementation(libs.kotlinx.rpc.transport.ktor.server)

    testImplementation(libs.ktor.server.tests)
//    testImplementation(libs.kotlinx.rpc.runtime.client)
//    testImplementation(libs.kotlinx.rpc.transport.ktor.client)
//    testImplementation(libs.kotlin.test.junit)
}
