package com.soujunior.petjournal.ui.util

import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun GetScreenInch(): Float {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val displayMetrics = context.resources.displayMetrics
    val screenWidthPixels = configuration.screenWidthDp * displayMetrics.density
    val screenHeightPixels = configuration.screenHeightDp * displayMetrics.density
    val diagonalPixels = sqrt(screenWidthPixels.pow(2) + screenHeightPixels.pow(2))
    val dpi = displayMetrics.densityDpi.toFloat()
    return diagonalPixels / dpi
}

@Composable
fun getScreenHeightInch(): Float {
    val configuration = LocalConfiguration.current
    val displayMetrics = LocalDensity.current.density
    val screenHeightDp = configuration.screenHeightDp * displayMetrics
    val scrollPercentage = 0.35f
    return screenHeightDp * scrollPercentage
}