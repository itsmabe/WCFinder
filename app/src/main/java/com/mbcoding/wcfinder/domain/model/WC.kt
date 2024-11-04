package com.mbcoding.wcfinder.domain.model

/**
 * Author: Mahmoud B.
 * Date: 31/10/2024
 * Description: WCEntity
 */

data class WC(
    val name: String?,
    val address: String?,
    val distance: Int?,
    val hours: String?,
    val latitude: Double?,
    val longitude: Double?
)
