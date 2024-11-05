package com.mbcoding.wcfinder.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.mbcoding.wcfinder.data.mapper.toWC
import com.mbcoding.wcfinder.data.remote.WCRepository
import javax.inject.Inject


/**
 * Author: Mahmoud B.
 * Date: 29/10/2024
 * Description: GetWCDataUseCase
 */

const val GEO_DISTANCE = 5000

class GetWCDataUseCase @Inject constructor(private val repository: WCRepository) {

    suspend fun execute(location: LatLng?, start: Int, rows: Int) =
        repository.getWCData(location, GEO_DISTANCE, start, rows)
            .records
            .orEmpty()
            .mapNotNull { it?.toWC() }
}