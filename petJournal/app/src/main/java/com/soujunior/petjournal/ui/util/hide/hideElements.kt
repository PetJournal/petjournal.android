package com.soujunior.petjournal.ui.util.hide

import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HideStatus(visible: Boolean = true) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = visible
}

@Composable
fun HideNavigation(visible: Boolean = true) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = visible
}