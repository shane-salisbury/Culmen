package com.weather.core.testing.location

import com.weather.core.location.LocationProvider
import com.weather.core.model.LocationCoordinates

class TestLocationProvider(
    private val locationCoordinates: LocationCoordinates?
) : LocationProvider {
    override fun getLocationCoordinates(onLocationReceived: (LocationCoordinates?) -> Unit) {
        onLocationReceived(locationCoordinates)
    }
}