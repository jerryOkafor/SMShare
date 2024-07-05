plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.cacheFix) apply false
//    alias(libs.plugins.kotlinx.rpc) apply false
//    alias(libs.plugins.kotlinx.rpc.platform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.buildKonfig) apply false
    alias(libs.plugins.io.gitlab.arturbosch.detekt) apply false
//    alias(libs.plugins.firebase.testLab) apply false
}
