package com.weather.feature.home

import com.weather.core.common.exception.LocationNotProvidedException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

val Throwable?.errorMessageResId: Int
    get() = when (this) {
        is UnknownHostException,
        is SocketTimeoutException -> R.string.error_network
        is LocationNotProvidedException -> R.string.error_location_not_provided
        else -> R.string.error_unknown
    }