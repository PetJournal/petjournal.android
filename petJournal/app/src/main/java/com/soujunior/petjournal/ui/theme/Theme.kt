package com.soujunior.petjournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColors = Colors(
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

private val DarkColors = Colors(
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

@Composable
fun PetJournalTheme(
    isDynamic: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
    setSystemBarColor: Boolean = true
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) DarkColors else LightColors
    if (setSystemBarColor) {
        SideEffect {
            systemUiController.setSystemBarsColor(color = colors.background)
        }
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content,
        )
}