package com.weather.core.network.model

import android.annotation.SuppressLint
import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
@Keep
data class NetworkTemperature(
    @SerialName("min")
    val min: Double,
    @SerialName("max")
    val max: Double
)
