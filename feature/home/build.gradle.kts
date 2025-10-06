plugins {
    alias(libs.plugins.weather.android.feature)
    alias(libs.plugins.weather.android.library.compose)
}

android {
    namespace = "com.weather.feature.home"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.data)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.core.location)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.kotlinx.datetime)

    implementation(libs.accompanist.permissions)

    testImplementation(projects.core.testing)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.truth)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
}