package com.soujunior.petjournal.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun notificationBarColor (isDarkTheme: Boolean) : Colors {
    val lightColors = Colors(
        primary = light_primary,
        onPrimary = light_onPrimary,
        primaryVariant = light_onPrimary,
        secondary = light_secondary,
        onSecondary = light_onSecondary,
        secondaryVariant = light_onSecondary,
        background = light_background,
        onBackground = light_onBackground,
        error = light_error,
        onError = light_onError,
        surface = light_surface,
        onSurface = light_onSurface,
        isLight = true
    )

    val darkColors = Colors(
        primary = dark_primary,
        onPrimary = dark_onPrimary,
        primaryVariant = dark_primaryVariant,
        secondary = dark_secondary,
        onSecondary = dark_onSecondary,
        secondaryVariant = dark_secondaryVariant,
        error = dark_error,
        onError = dark_onError,
        background = dark_background,
        onBackground = dark_onBackground,
        surface = dark_surface,
        onSurface = dark_onSurface,
        isLight = false
    )


    val systemUiController = rememberSystemUiController()
    val colors = if (isDarkTheme) darkColors else lightColors
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = colors.background
        )
    }
    return colors
}