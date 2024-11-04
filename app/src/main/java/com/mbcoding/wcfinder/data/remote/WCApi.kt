package com.mbcoding.wcfinder.data.remote

import com.google.android.gms.maps.model.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

/**
 * Author: Mahmoud B.
 * Date: 29/10/2024
 * Description: ApiService
 */

class WCApi @Inject constructor(private val client: HttpClient) {

    companion object {
        const val BASE_URL = "https://data.ratp.fr/api/records/1.0/search/"
    }

    suspend fun getWCData(location: LatLng?, distance: Int, start: Int, rows: Int) =
        client.get(BASE_URL) {
            parameter("dataset", "sanisettesparis2011")
            parameter(
                "geofilter.distance",
                "${location?.latitude},${location?.longitude},$distance"
            )
            parameter("rows", rows)
            parameter("start", start)
        }.body<WCData>()
}