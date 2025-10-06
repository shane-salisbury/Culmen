plugins {
    alias(libs.plugins.weather.android.library)
}

android {
    namespace = "com.weather.core.common"
}

dependencies {
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.truth)
}