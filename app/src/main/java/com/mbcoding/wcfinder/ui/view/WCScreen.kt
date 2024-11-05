package com.mbcoding.wcfinder.ui.view

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.mbcoding.wcfinder.domain.model.WC
import com.mbcoding.wcfinder.ui.intent.WCIntent.GetCurrentLocation
import com.mbcoding.wcfinder.ui.intent.WCIntent.GetWCData
import com.mbcoding.wcfinder.ui.state.CurrentLocationState.Error
import com.mbcoding.wcfinder.ui.state.CurrentLocationState.Success
import com.mbcoding.wcfinder.ui.viewmodel.WCViewModel
import com.mbcoding.wcfinder.utils.hasLocationPermission
import kotlinx.coroutines.launch

/**
 * Author: Mahmoud B.
 * Date: 01/11/2024
 * Description: WCScreen
 */

@Composable
fun WCScreen(viewModel: WCViewModel, cameraPositionState: CameraPositionState) {
    val wcList = viewModel.dataState.collectAsLazyPagingItems()
    val locationState by viewModel.locationState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.processIntent(GetCurrentLocation)
                scope.launch {
                    when (locationState) {
                        is Success -> {
                            currentLocation = (locationState as Success).location
                            viewModel.processIntent(GetWCData, currentLocation)
                        }

                        is Error -> Toast.makeText(
                            context,
                            (locationState as Error).error,
                            Toast.LENGTH_LONG
                        ).show()

                        else -> {}
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (context.hasLocationPermission()) {
            viewModel.processIntent(GetCurrentLocation)
            scope.launch {
                when (locationState) {
                    is Success -> {
                        currentLocation = (locationState as Success).location
                        viewModel.processIntent(GetWCData, currentLocation)
                    }

                    is Error -> Toast.makeText(
                        context,
                        (locationState as Error).error,
                        Toast.LENGTH_LONG
                    ).show()

                    else -> {}
                }
            }
        } else launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    LaunchedEffect(key1 = wcList.loadState) {
        if (wcList.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                (wcList.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    if (locationState is Success) {
        WCMap(wcList = wcList, cameraPositionState, (locationState as Success).location)
        WCBottomSheet(wcList = wcList, cameraPositionState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WCBottomSheet(wcList: LazyPagingItems<WC>, cameraPositionState: CameraPositionState) {
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    )
    BottomSheetScaffold(
        modifier = Modifier.background(color = Color.White),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 150.dp,
        sheetContainerColor = Color.White,
        sheetContent = {
            if (wcList.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                WCList(wcList) { wc ->
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    wc?.latitude!!,
                                    wc.longitude!!
                                ), 18f
                            ), durationMs = 2000
                        )
                    }
                }
            }
        }) {}
}