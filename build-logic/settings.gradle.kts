dependencyResolutionManagement {
    repositories {
        google()
        maven(url = "https://plugins.gradle.org/m2/")
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

buildCache {
    val remoteBuildCacheUrl = providers
        .gradleProperty(
            "REMOTE_BUILD_CACHE_URL",
        ).orNull ?: return@buildCache
    val isCi = providers.environmentVariable("CI").isPresent

    local {
        isEnabled = !isCi
    }

    remote(HttpBuildCache::class) {
        url = uri(remoteBuildCacheUrl)
        isPush = isCi

        credentials {
            username = providers.gradleProperty("REMOTE_BUILD_CACHE_USERNAME").orNull
            password = providers.gradleProperty("REMOTE_BUILD_CACHE_PASSWORD").orNull
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
