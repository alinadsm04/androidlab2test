import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
}

// Place the version of your library here
val versionName: String = "1.0.1"

// Add the name of your library here
val libArtifactId: String = "chat"

// Add the group ID of your library here
val libGroupId: String = "kz.alinadsm04"

// Prepare URL of maven package.
val gitHubUrl: String = "https://maven.pkg.github.com/alinadsm04/chat"


android {
    namespace = "kz.alina.chat"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures{
        viewBinding = true
    }
}

publishing {
    publications {
        register<MavenPublication>("release")  {
            groupId = libGroupId
            artifactId = libArtifactId
            version = versionName
            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri(gitHubUrl)
            credentials {
                username = "alinadsm04"
                password = "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"
            }
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
}