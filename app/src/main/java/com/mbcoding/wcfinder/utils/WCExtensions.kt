package com.mbcoding.wcfinder.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.tasks.await

@SuppressLint("MissingPermission")
suspend fun Context.currentLocation(): LatLng? {
    if (!this.hasLocationPermission())
        throw SecurityException("Location permission not granted")

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    return try {
        fusedLocationClient.lastLocation.await().toLatLng()
    } catch (e: Exception) {
        null
    }
}

fun Context.hasLocationPermission() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.ACCESS_FINE_LOCATION
) == PackageManager.PERMISSION_GRANTED

fun Location.toLatLng() = LatLng(this.latitude, this.longitude)