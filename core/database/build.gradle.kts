import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.smshare.android.library)
    alias(libs.plugins.smshare.kotlin.multiplatform)
    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)
        }
    }

    sourceSets {
        androidUnitTest {
            dependencies {
                implementation(libs.junit)
                implementation(libs.androidx.test.junit)
                implementation("org.robolectric:robolectric:4.12.2")
            }
        }
        androidInstrumentedTest {
            dependencies {
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.test.junit.ktx)
                implementation(libs.androidx.espresso.core)
                implementation(libs.google.truth)
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
                implementation(libs.androidx.sqlite.bundled)
                implementation(libs.koin.core)
                implementation(libs.kotlinx.datetime)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.test.common)
                implementation(libs.kotlin.test.annotations.common)
            }
        }

        jvmTest.dependencies {
            implementation(libs.kotlin.test)
//            implementation(libs.kotlin.test.junit5)
            implementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
            implementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
            implementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
            implementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
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
        androidTestImplementation(libs.androidx.test.runner)
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
