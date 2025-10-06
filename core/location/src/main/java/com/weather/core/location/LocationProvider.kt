package com.weather.core.location

import com.weather.core.model.LocationCoordinates

interface LocationProvider {

    fun getLocationCoordinates(onLocationReceived: (LocationCoordinates?) -> Unit)
}