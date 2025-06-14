import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.smshare.android.library)
    alias(libs.plugins.smshare.kotlin.multiplatform)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.smshare.detekt)
    alias(libs.plugins.smshare.ktlint)
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
        
        //LinkedIn
        buildConfigField(STRING, "linkedInClientId", secretProps["LINKEDIN_CLIENT_ID"] as String)
        buildConfigField(STRING, "redirectUrl", secretProps["REDIRECT_URL"] as String)
        buildConfigField(
            STRING,
            "linkedInClientSecret",
            secretProps["LINKEDIN_CLIENT_SECRET"] as String,
        )
        
        // X or Twitter
        buildConfigField(STRING,"xClientId",secretProps["X_OAUTH_CLIENT_ID"] as String)
    }

    targetConfigs {
        // names in create should be the same as target names you specified
        create("android") {}
        create("ios") {}
        create("jvm") {}
    }
}
