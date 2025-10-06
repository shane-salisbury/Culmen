plugins {
    alias(libs.plugins.weather.android.library)
    alias(libs.plugins.weather.hilt)
}

android {
    namespace = "com.weather.core.location"
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.play.services.location)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.truth)
}