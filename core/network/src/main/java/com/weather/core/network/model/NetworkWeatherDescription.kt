package com.weather.core.network.model

import android.annotation.SuppressLint
import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
@Keep
data class NetworkWeatherDescription(
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)