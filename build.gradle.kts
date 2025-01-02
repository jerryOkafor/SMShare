plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.cacheFix) apply false
//    alias(libs.plugins.kotlinx.rpc) apply false
    alias(libs.plugins.buildKonfig) apply false
    alias(libs.plugins.io.gitlab.arturbosch.detekt) apply false
    alias(libs.plugins.kotlinx.kover)
    id("org.flywaydb.flyway") version "8.5.4" apply false
}

kover {
    merge {
        allProjects()
    }
    reports {
        total {
            filters {
                excludes {
                    classes(".*BuildConfig.*")
                }
            }

            xml {
                title = "SM Share XML Report"
                onCheck = false
                xmlFile = layout.buildDirectory.file("/kover/$name/report.xml").get()
            }

            html {
                title = "SM Share HTML Report"
                onCheck = false
                charset = "UTF-8"
                htmlDir.set(layout.buildDirectory.dir("/kover/$name/report/html").get())
            }
            binary {
                file = layout.buildDirectory.file("/kover/$name/report.ic").get()
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
