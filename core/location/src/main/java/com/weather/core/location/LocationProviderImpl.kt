package com.weather.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.weather.core.model.LocationCoordinates
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
): LocationProvider {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun getLocationCoordinates(onLocationReceived: (LocationCoordinates?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location: Location? ->
            onLocationReceived(location?.toLocationCoordinates())
        }
    }
}