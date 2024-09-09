plugins {
    alias(libs.plugins.smshare.android.library)
    alias(libs.plugins.smshare.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)

}

kotlin {
    sourceSets {
        androidUnitTest {
            dependencies {
                implementation(libs.junit)
                implementation(libs.androidx.test.junit)
                implementation(libs.robolectric)
            }
        }
        commonMain {
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

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspDesktop", libs.androidx.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
    generateKotlin = true
}
