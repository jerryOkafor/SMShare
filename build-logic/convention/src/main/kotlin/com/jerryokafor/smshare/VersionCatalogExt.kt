package com.jerryokafor.smshare

import org.gradle.api.Action
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.ModuleIdentifier
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.internal.catalog.ExternalModuleDependencyFactory
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo

fun DependencyHandler.implementations(
    dependencyNotation: ExternalModuleDependencyFactory,
    dependencyConfiguration: Action<ExternalModuleDependency>,
): ExternalModuleDependency =
    addDependencyTo(
        this,
        "implementation",
        dependencyNotation,
        dependencyConfiguration,
    )

fun ModuleDependency.exclude(
    group: Provider<MinimalExternalModuleDependency>? = null,
    module: Provider<MinimalExternalModuleDependency>? = null,
) {
    check((group != null) xor (module != null)) {
        "Usage: exclude(libs.foo), exclude(group = libs.foo) or exclude(module = libs.foo)."
    }
    group?.let { this.exclude(it.asGroup()) }
    module?.let { this.exclude(it.asModule()) }
}

private fun Provider<MinimalExternalModuleDependency>.asGroup(): Map<String, String> = this.get().module.asGroup()

private fun ModuleIdentifier.asGroup(): Map<String, String> =
    mapOf(
        "group" to this.group,
    )

private fun Provider<MinimalExternalModuleDependency>.asModule(): Map<String, String> = this.get().module.asModule()

private fun ModuleIdentifier.asModule(): Map<String, String> =
    mapOf(
        "module" to this.name,
    )
