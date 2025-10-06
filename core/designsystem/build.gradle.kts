plugins {
    alias(libs.plugins.weather.android.library)
    alias(libs.plugins.weather.android.library.compose)
}

android {
    namespace = "com.weather.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3.navigationSuite)
}