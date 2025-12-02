plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
}