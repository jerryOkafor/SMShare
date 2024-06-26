/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023 IheNkiri Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jerryokafor.smshare

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.attributes.Bundling
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.named
import org.gradle.language.base.plugins.LifecycleBasePlugin

class KtLintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val version = libs.findVersion("ktlint").get().toString()
        val ktlint by configurations.creating
        val outputDir = layout.buildDirectory.dir("reports/ktlint").get()
        val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

        tasks.register("ktlintCheck", JavaExec::class.java) {
            inputs.files(inputFiles)
            outputs.dir(outputDir)

            group = LifecycleBasePlugin.VERIFICATION_GROUP
            description = "Check Kotlin code style."
            classpath = ktlint
            mainClass.set("com.pinterest.ktlint.Main")
            args(
                "**/src/**/*.kt",
                "**.kts",
                "!**/build/**",
                "--baseline=$rootDir/config/ktlint/baseline.xml",
            )
        }

        tasks.register("ktlintFormat", JavaExec::class.java) {
            inputs.files(inputFiles)
            outputs.dir(outputDir)

            group = LifecycleBasePlugin.VERIFICATION_GROUP
            description = "Fix Kotlin code style deviations."
            classpath = ktlint
            mainClass.set("com.pinterest.ktlint.Main")
            jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
            args(
                "-F",
                "**/src/**/*.kt",
                "**.kts",
                "!**/build/**",
                "--baseline=$rootDir/config/ktlint/baseline.xml",
            )
        }

        tasks.register("ktlintFormatBaseline", JavaExec::class.java) {
            inputs.files(inputFiles)
            outputs.dir(outputDir)

            group = LifecycleBasePlugin.VERIFICATION_GROUP
            description = "Fix Kotlin code style deviations."
            classpath = ktlint
            mainClass.set("com.pinterest.ktlint.Main")
            jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
            args(
                "-F",
                "**/src/**/*.kt",
                "**.kts",
                "!**/build/**",
                "--baseline=$rootDir/config/ktlint/baseline.xml",
            )
        }

        tasks.register("ktlintAndroidStudio", JavaExec::class.java) {
            description = "Setup Android Studio to use the same code style as ktlint."
            classpath = ktlint
            mainClass.set("com.pinterest.ktlint.Main")
            args = listOf(
                "--apply-to-idea-project",
                "-y",
                "--baseline=$rootDir/config/ktlint/baseline.xml",
            )
            workingDir(rootDir)
        }

        dependencies {
            ktlint("com.pinterest.ktlint:ktlint-cli:$version") {
                attributes {
                    attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
                }
            }
            // ktlintConfig(project(":custom-ktlint-ruleset")) // in case of custom ruleset
        }
    }
}
