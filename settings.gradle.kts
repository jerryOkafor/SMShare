rootProject.name = "SMShare"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
// https://docs.gradle.org/7.6/userguide/configuration_cache.html#config_cache:stable
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/krpc/maven")
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/krpc/maven")
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

val isCi = providers.environmentVariable("CI").isPresent

buildCache {
    val remoteBuildCacheUrl = providers.gradleProperty("REMOTE_BUILD_CACHE_URL").orNull ?: return@buildCache

    local {
        isEnabled = !isCi
    }

    remote(HttpBuildCache::class) {
        url = uri(remoteBuildCacheUrl)
        isPush = isCi

        println("Enabling remote build cache. URL: $url. Push enabled: $isPush")

        credentials {
            username = providers.gradleProperty("REMOTE_BUILD_CACHE_USERNAME").orNull
            password = providers.gradleProperty("REMOTE_BUILD_CACHE_PASSWORD").orNull
        }
    }
}

include(":composeApp")
include(":server")
include(":shared")
// include(":core:rpc")
include(":core:model")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:domain")
include(":core:config")
