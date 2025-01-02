plugins {
    alias(libs.plugins.smshare.android.library)
    alias(libs.plugins.smshare.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
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

    sourceSets {
        commonMain.dependencies {
//            api(projects.core.rpc)
            api(projects.core.model)

            api(libs.kotlinx.coroutines.core)
            api(libs.co.touchlab.kermit)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.atomicfu)

            implementation(libs.koin.core)

            api(libs.androidx.datastore.core.okio)
            api(libs.androidx.datastore.preferences.core)
            implementation(libs.squareup.okio)
        }
    }
}

android {
    namespace = "com.jerryokafor.smshare.core.common"
}
