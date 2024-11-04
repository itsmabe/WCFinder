package com.mbcoding.wcfinder.data.mapper

import com.mbcoding.wcfinder.data.remote.Record
import com.mbcoding.wcfinder.domain.model.WC
import java.util.Locale


/**
 * Author: Mahmoud B.
 * Date: 31/10/2024
 * Description: WCMappers
 */

fun Record.toWC() = WC(
    name = fields?.type,
    address = fields?.adresse?.uppercase(Locale.FRENCH),
    distance = fields?.dist?.toFloat()?.toInt(),
    hours = fields?.horaire,
    latitude = geometry?.coordinates?.get(1),
    longitude = geometry?.coordinates?.get(0)
)