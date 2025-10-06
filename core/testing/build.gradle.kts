plugins {
    alias(libs.plugins.weather.android.library)
}

android {
    namespace = "com.weather.core.testing"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.location)
    implementation(projects.core.network)

    implementation(libs.kotlinx.datetime)

    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
}