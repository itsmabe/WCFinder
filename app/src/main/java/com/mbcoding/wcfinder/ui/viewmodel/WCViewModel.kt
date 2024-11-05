package com.mbcoding.wcfinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.android.gms.maps.model.LatLng
import com.mbcoding.wcfinder.utils.WCPagingSource
import com.mbcoding.wcfinder.domain.model.WC
import com.mbcoding.wcfinder.domain.usecase.GetCurrentLocationUseCase
import com.mbcoding.wcfinder.domain.usecase.GetWCDataUseCase
import com.mbcoding.wcfinder.ui.intent.WCIntent
import com.mbcoding.wcfinder.ui.state.CurrentLocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Mahmoud B.
 * Date: 01/11/2024
 * Description: WCViewModel
 */

@HiltViewModel
class WCViewModel @Inject constructor(
    private val useCase: GetWCDataUseCase,
    private val locationUseCase: GetCurrentLocationUseCase
) : ViewModel() {

    private val _locationState =
        MutableStateFlow<CurrentLocationState<LatLng>>(CurrentLocationState.Idle)
    val locationState: StateFlow<CurrentLocationState<LatLng>>
        get() = _locationState

    private val _dataState = MutableStateFlow(PagingData.empty<WC>())
    val dataState: StateFlow<PagingData<WC>>
        get() = _dataState

    fun processIntent(intent: WCIntent, location: LatLng? = null) {
        viewModelScope.launch {
            when (intent) {
                is WCIntent.GetWCData -> fetchDataWithPaging(location)
                is WCIntent.GetCurrentLocation -> getCurrentLocation()
            }
        }
    }

    private fun getCurrentLocation() {
        locationUseCase.execute().onEach { state ->
            when (state) {
                is CurrentLocationState.Success ->
                    _locationState.value = CurrentLocationState.Success(state.location)

                is CurrentLocationState.Error ->
                    _locationState.value = CurrentLocationState.Error(state.error)

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun fetchDataWithPaging(location: LatLng?) {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { WCPagingSource(useCase, location) }
        ).flow.cachedIn(viewModelScope).collect { _dataState.value = it }
    }
}