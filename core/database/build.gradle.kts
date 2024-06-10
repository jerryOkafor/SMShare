plugins {
    id("com.jerryokafor.smshare.android.library")
    id("com.jerryokafor.smshare.multiplatform")
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "com.jerryokafor.core.database"
}

kotlin.sourceSets.iosMain {
    kotlin.srcDir("build/generated/ksp/metadata")
}

dependencies {
    add("kspCommonMainMetadata", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
