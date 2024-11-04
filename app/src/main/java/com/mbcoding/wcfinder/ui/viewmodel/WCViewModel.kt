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
import com.mbcoding.wcfinder.domain.usecase.GetWCDataUseCase
import com.mbcoding.wcfinder.ui.intent.WCIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Mahmoud B.
 * Date: 01/11/2024
 * Description: WCViewModel
 */

@HiltViewModel
class WCViewModel @Inject constructor(private val useCase: GetWCDataUseCase) : ViewModel() {

    private val _state = MutableStateFlow(PagingData.empty<WC>())
    val state: StateFlow<PagingData<WC>>
        get() = _state

    fun processIntent(intent: WCIntent, location: LatLng?) {
        viewModelScope.launch {
            when (intent) {
                is WCIntent.GetWCData -> fetchDataWithPaging(location)
            }
        }
    }

    private suspend fun fetchDataWithPaging(location: LatLng?) {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { WCPagingSource(useCase, location) }
        ).flow.cachedIn(viewModelScope).collect { _state.value = it }
    }
}