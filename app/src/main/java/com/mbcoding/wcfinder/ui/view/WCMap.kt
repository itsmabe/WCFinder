package com.mbcoding.wcfinder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import com.mbcoding.wcfinder.R
import com.mbcoding.wcfinder.domain.model.WC
import kotlinx.coroutines.launch

@Composable
fun WCMap(
    wcList: LazyPagingItems<WC>,
    cameraPositionState: CameraPositionState,
    currentLocation: LatLng
) {
    val scope = rememberCoroutineScope()
    val currentMarkerState = rememberMarkerState()

    LaunchedEffect(Unit) {
        scope.launch {
            currentMarkerState.position = currentLocation
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(currentLocation, 14f)
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState) {
            Marker(
                state = currentMarkerState,
                icon = BitmapDescriptorFactory.fromResource(R.drawable.mylocation)
            )
            wcList.itemSnapshotList.forEach { wc ->
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            wc?.latitude!!,
                            wc.longitude!!
                        )
                    ),
                    title = wc.name,
                    snippet = wc.address
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 160.dp, end = 10.dp),
            containerColor = Color.White,
            shape = CircleShape,
            onClick = {
                scope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(currentLocation, 16f),
                        durationMs = 2000
                    )
                }
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_near_me_24),
                contentDescription = "Near me"
            )
        }
    }
}