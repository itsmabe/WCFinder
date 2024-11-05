package com.mbcoding.wcfinder.ui.state

sealed class CurrentLocationState<out T> {
    data class Success<T>(val location: T) : CurrentLocationState<T>()
    data class Error<T>(val error: String) : CurrentLocationState<T>()
    data object Idle : CurrentLocationState<Nothing>()
}