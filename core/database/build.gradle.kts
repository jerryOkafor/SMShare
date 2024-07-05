plugins {
    alias(libs.plugins.smshare.android.library)
    alias(libs.plugins.smshare.kotlin.multiplatform)
    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.smshare.coverage)
}

kotlin {
    androidTarget {}

    sourceSets {
        androidUnitTest {
            dependencies {
                implementation(libs.junit)
                implementation(libs.androidx.test.junit)
                implementation(libs.robolectric)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.room.runtime.android)
            }
        }
        commonMain {
            kotlin {
                srcDir("build/generated/ksp/metadata")
            }
            dependencies {
                implementation(projects.core.model)

                api(libs.androidx.room.runtime)
                api(libs.androidx.sqlite.bundled)

                implementation(libs.koin.core)
                implementation(libs.kotlinx.datetime)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotlin.test)

//                implementation(libs.kotlin.test.common)
//                implementation(libs.kotlin.test.annotations.common)
            }
        }
    }
}

android {
    namespace = "com.jerryokafor.core.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments += mutableMapOf("clearPackageData" to "true")
    }

    dependencies {
        testImplementation(libs.junit)
    }
}

// https://issuetracker.google.com/issues/342905180
// More info: https://github.com/google/ksp/blob/00862a18967eed6832b28e081212e5f3250eb191/examples/multiplatform/workload/build.gradle.kts#L43
dependencies {
    add("kspCommonMainMetadata", libs.androidx.room.compiler)
//    add("kspAndroid", libs.androidx.room.compiler)
//    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
//    add("kspIosX64", libs.androidx.room.compiler)
//    add("kspIosArm64", libs.androidx.room.compiler)
//    add("kspJvm", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
    generateKotlin = true
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
