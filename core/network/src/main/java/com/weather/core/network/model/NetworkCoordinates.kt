package com.weather.core.network.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NetworkCoordinates(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double
)
