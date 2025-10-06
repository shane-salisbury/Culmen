plugins {
    alias(libs.plugins.weather.android.application)
    alias(libs.plugins.weather.android.application.compose)
    alias(libs.plugins.weather.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.weather"

    defaultConfig {
        applicationId = "com.weather"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.location)

    implementation(projects.feature.home)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
}