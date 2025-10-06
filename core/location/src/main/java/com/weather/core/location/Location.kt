package com.weather.core.location

import android.location.Location
import com.weather.core.model.LocationCoordinates

fun Location.toLocationCoordinates() = LocationCoordinates(
    latitude = latitude,
    longitude = longitude
)