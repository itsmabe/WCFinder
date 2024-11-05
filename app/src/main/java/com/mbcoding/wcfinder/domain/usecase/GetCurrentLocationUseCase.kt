package com.mbcoding.wcfinder.domain.usecase

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.mbcoding.wcfinder.ui.state.CurrentLocationState
import com.mbcoding.wcfinder.utils.toLatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Author: Mahmoud B.
 * Date: 03/11/2024
 * Description: GetCurrentLocationUseCase
 */

class GetCurrentLocationUseCase @Inject constructor(@ApplicationContext private val context: Context) {

    @SuppressLint("MissingPermission")
    fun execute() = flow {
        try {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            val location = fusedLocationClient.lastLocation.await().toLatLng()
            emit(CurrentLocationState.Success(location))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(CurrentLocationState.Error(e.message ?: "Une erreur s'est produite.."))
        }
    }
}