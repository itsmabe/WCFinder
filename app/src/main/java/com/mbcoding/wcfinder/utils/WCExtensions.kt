package com.mbcoding.wcfinder.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng

fun Context.hasLocationPermission() = ContextCompat.checkSelfPermission(
    this, Manifest.permission.ACCESS_FINE_LOCATION
) == PackageManager.PERMISSION_GRANTED

fun Location.toLatLng() = LatLng(this.latitude, this.longitude)