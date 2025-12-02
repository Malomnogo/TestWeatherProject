import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

kotlin {
    jvmToolchain(17)
}

val secretsPropertiesFile = rootProject.file("secrets.properties")
val secretsProperties = Properties()
if (secretsPropertiesFile.exists()) {
    secretsPropertiesFile.inputStream().use {
        secretsProperties.load(it)
    }
}

android {
    namespace = "com.malomnogo.testweatherproject"
    compileSdk {
        version = release(36)
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.malomnogo.testweatherproject"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.malomnogo.testweatherproject.TestRunner"

        buildConfigField(
            "String",
            "WEATHER_API_KEY",
            "\"${secretsProperties["WEATHER_API_KEY"] ?: ""}\""
        )
    }

    flavorDimensions += "environment"

    productFlavors {
        create("real") {
            dimension = "environment"
            isDefault = true
        }

        create("mock") {
            dimension = "environment"
            applicationIdSuffix = ".mock"
        }
    }

    buildTypes {
        debug {
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        animationsDisabled = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

afterEvaluate {
    tasks.register("testWithMocks") {
        group = "verification"
        description = "Runs UI tests with mockDebug variant (automatically uses mocks)"
        dependsOn("connectedMockDebugAndroidTest")
    }
}