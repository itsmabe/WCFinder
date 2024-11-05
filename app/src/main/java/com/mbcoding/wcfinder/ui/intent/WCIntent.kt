package com.mbcoding.wcfinder.ui.intent


/**
 * Author: Mahmoud B.
 * Date: 29/10/2024
 * Description: WCIntent
 */

sealed class WCIntent {
    data object GetWCData : WCIntent()
    data object GetCurrentLocation : WCIntent()
}