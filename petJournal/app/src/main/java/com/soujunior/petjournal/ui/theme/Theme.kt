package com.soujunior.petjournal.ui.theme

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**h1	displayLarge
h2	displayMedium
h3	displaySmall
h4	headlineLarge
h5	headlineMedium
h6	headlineSmall
subtitle1	titleLarge
subtitle2	titleMedium
body1	bodyLarge
body2	bodyMedium
button	bodyMedium ou titleSmall (dependendo do contexto)
caption		labelSmall
overline	labelSmall

colorScheme
 */

val lightExtendedColors: ExtendedColors
    @Composable
    get() = ExtendedColors(
        snowWhite = Color.Cyan,
        deepOcean = Color.Cyan,
        skyBlue = Color.Cyan,
        nightBlue = Color.Cyan,
        dialogBackground = Color.Cyan,
    )

val darkExtendedColors: ExtendedColors
    @Composable
    get() = ExtendedColors(
        snowWhite = Color.Blue,
        deepOcean = Color.Blue,
        skyBlue = Color.Blue,
        nightBlue = Color.Blue,
        dialogBackground = Color.Blue,
    )

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        snowWhite = Color.Red,
        deepOcean = Color.Red,
        skyBlue = Color.Red,
        nightBlue = Color.Red,
        dialogBackground = Color.Red
    )
}

@Immutable
data class ExtendedColors(
    val snowWhite: Color,
    val deepOcean: Color,
    val skyBlue: Color,
    val nightBlue: Color,
    val dialogBackground: Color
)

val lightCor: ColorScheme
    @Composable get() = lightColorScheme(
        primary = light_primary,
        onPrimary = light_onPrimary,
        primaryContainer = light_primaryContainer,
        onPrimaryContainer = light_onPrimaryContainer,
        inversePrimary = light_inversePrimary,
        secondary = light_secondary,
        onSecondary = light_onSecondary,
        secondaryContainer = light_secondaryContainer,
        onSecondaryContainer = light_onSecondaryContainer,
        tertiary = light_tertiary,
        onTertiary = light_onTertiary,
        tertiaryContainer = light_tertiaryContainer,
        onTertiaryContainer = light_onTertiaryContainer,
        background = light_background,
        onBackground = light_onBackground,
        surface = light_surface,
        onSurface = light_onSurface,
        surfaceVariant = light_surfaceVariant,
        onSurfaceVariant = light_onSurfaceVariant,
        surfaceTint = light_surfaceTint,
        inverseSurface = light_inverseSurface,
        inverseOnSurface = light_inverseOnSurface,
        error = light_error,
        onError = light_onError,
        errorContainer = light_errorContainer,
        onErrorContainer = light_onErrorContainer,
        outline = light_outline,
        outlineVariant = light_outlineVariant,
        scrim = light_scrim,

    )

val DarkCor: ColorScheme
    @Composable get() = darkColorScheme(
        primary = dark_primary, //textos
        onPrimary = dark_onPrimary,
        secondary = dark_secondary,
        onSecondary = dark_onSecondary,
        onTertiary = dark_primary,
        background = dark_background,
        onBackground = dark_onBackground,
        surface = dark_surface,
        onSurface = dark_onSurface,
        surfaceVariant = dark_onPrimary,
        onSurfaceVariant = dark_outline,
        scrim = dark_scrim,
        tertiary = dark_tertiary,
        surfaceTint = dark_surfaceTint,
        inversePrimary = dark_inversePrimary,
        inverseSurface = dark_inverseSurface,
        errorContainer = dark_errorContainer,
        primaryContainer = dark_primaryContainer,
        inverseOnSurface = dark_inverseOnSurface,
        onErrorContainer = dark_onErrorContainer,
        tertiaryContainer = dark_tertiaryContainer,
        secondaryContainer = dark_secondaryContainer,
        onPrimaryContainer = dark_onPrimaryContainer,
        onTertiaryContainer = dark_onTertiaryContainer,
        onSecondaryContainer = dark_onSecondaryContainer,
        error = dark_error,
        onError = dark_onError,
        outline = dark_outline,
        outlineVariant = dark_outlineVariant,
    )

@Composable
fun PetJournalTheme(
    isDynamic: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
    setSystemBarColor: Boolean = true
) {
    val systemUiController = rememberSystemUiController()

    val useDynamicColor = isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val extendedColors = if (darkTheme) darkExtendedColors else lightExtendedColors

    val colors = if (useDynamicColor) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) DarkCor else lightCor
    }

    if (setSystemBarColor) {
        SideEffect {
            systemUiController.setSystemBarsColor(color = colors.background)
            systemUiController.setNavigationBarColor(color = Color.Black)
        }
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content,
        )
    }
}

@Preview
@Composable
fun PreviewColorTheme(){
    ColorSchemePreview(lightCor)
}
@Composable
fun ColorSchemePreview(
    lightCor: ColorScheme
) {
    Column {
        with(lightCor) {
            processColor("primary", primary)
            processColor("onPrimary", onPrimary)
            processColor("primaryContainer", primaryContainer)
            processColor("onPrimaryContainer", onPrimaryContainer)
            processColor("inversePrimary", inversePrimary)
            processColor("secondary", secondary)
            processColor("onSecondary", onSecondary)
            processColor("secondaryContainer", secondaryContainer)
            processColor("onSecondaryContainer", onSecondaryContainer)
            processColor("tertiary", tertiary)
            processColor("onTertiary", onTertiary)
            processColor("tertiaryContainer", tertiaryContainer)
            processColor("onTertiaryContainer", onTertiaryContainer)
            processColor("background", background)
            processColor("onBackground", onBackground)
            processColor("surface", surface)
            processColor("onSurface", onSurface)
            processColor("surfaceVariant", surfaceVariant)
            processColor("onSurfaceVariant", onSurfaceVariant)
            processColor("surfaceTint", surfaceTint)
            processColor("inverseSurface", inverseSurface)
            processColor("inverseOnSurface", inverseOnSurface)
            processColor("error", error)
            processColor("onError", onError)
            processColor("errorContainer", errorContainer)
            processColor("onErrorContainer", onErrorContainer)
            processColor("outline", outline)
            processColor("outlineVariant", outlineVariant)
            processColor("scrim", scrim)
        }
    }
}

@Composable
fun processColor(name: String, color: Color) {
    Row(modifier = Modifier.background(color)) {
        Text(text = name)
    }
}