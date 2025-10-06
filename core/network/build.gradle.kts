plugins {
    alias(libs.plugins.weather.android.library)
    alias(libs.plugins.weather.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.weather.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // OkHttp & Retrofit
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.kotlinx.datetime)

    testImplementation(projects.core.testing)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.truth)
}