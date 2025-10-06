plugins {
    alias(libs.plugins.weather.android.library)
    alias(libs.plugins.weather.hilt)
}

android {
    namespace = "com.weather.core.data"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.model)

    implementation(libs.kotlinx.datetime)

    testImplementation(projects.core.testing)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.truth)
}