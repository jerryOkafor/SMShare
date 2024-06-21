import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.jerryokafor.smshare.android.library")
    id("com.jerryokafor.smshare.multiplatform")
    id("com.jerryokafor.smshare.android.detekt")
    id("com.jerryokafor.smshare.android.ktlint")
    alias(libs.plugins.buildKonfig)
}

val secretPropsFile = rootProject.file("secrets.properties")
val secretProps = Properties()
secretProps.load(FileInputStream(secretPropsFile))

android {
    namespace = "com.jerryokafor.smshare.core.config"
}

buildkonfig {
    packageName = "com.jerryokafor.smshare.core.config"
    objectName = "SMShareConfig"
    exposeObjectWithName = "SMShareConfig"

    // default config is required
    defaultConfigs {
        buildConfigField(STRING, "linkedInClientId", secretProps["LINKEDIN_CLIENT_ID"] as String)
        buildConfigField(STRING, "redirectUrl", secretProps["REDIRECT_URL"] as String)
        buildConfigField(
            STRING,
            "linkedInClientSecret",
            secretProps["LINKEDIN_CLIENT_SECRET"] as String,
        )
    }

    targetConfigs {
        // names in create should be the same as target names you specified
        create("android") {}
        create("ios") {}
        create("jvm") {}
    }
}
