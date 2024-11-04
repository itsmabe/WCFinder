package com.mbcoding.wcfinder.data.remote

import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject


/**
 * Author: Mahmoud B.
 * Date: 29/10/2024
 * Description: RemoteRepository
 */

class WCRepository @Inject constructor(private val api: WCApi) {

    suspend fun getWCData(location: LatLng?, distance: Int, start: Int, rows: Int) =
        api.getWCData(location, distance, start, rows)
}