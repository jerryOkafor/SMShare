plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser {
//            commonWebpackConfig {
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(project.projectDir.path)
//                    }
//                }
//            }
//        }
//    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
//            api(projects.core.rpc)
            api(projects.core.model)

            api(libs.kotlinx.coroutines.core)
            api(libs.co.touchlab.kermit)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.atomicfu)

            api(libs.androidx.datastore.core.okio)
            api(libs.androidx.datastore.preferences.core)
            implementation(libs.squareup.okio)
        }
    }
}

kotlin.sourceSets.all {
    languageSettings {
        optIn("kotlin.experimental.ExperimentalObjCName")
        optIn("kotlinx.cinterop.ExperimentalForeignApi")
    }
}

android {
    namespace = "com.jerryokafor.smshare.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
