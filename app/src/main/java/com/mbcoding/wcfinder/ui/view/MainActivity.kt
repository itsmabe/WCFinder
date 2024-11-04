package com.mbcoding.wcfinder.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.maps.android.compose.rememberCameraPositionState
import com.mbcoding.wcfinder.ui.theme.WCFinderTheme
import com.mbcoding.wcfinder.ui.viewmodel.WCViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Author: Mahmoud B.
 * Date: 01/11/2024
 * Description: MainActivity
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WCFinderTheme {
                val viewModel: WCViewModel by viewModels()
                val cameraPositionState = rememberCameraPositionState()
                WCScreen(viewModel = viewModel, cameraPositionState)
            }
        }
    }
}