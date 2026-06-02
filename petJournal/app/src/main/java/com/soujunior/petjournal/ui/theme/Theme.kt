package com.soujunior.petjournal.ui.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.soujunior.domain.use_case.preference.GetDarkModePreferenceUseCase
import com.soujunior.domain.use_case.preference.GetSystemThemePreferenceUseCase
import org.koin.androidx.compose.get

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

@Immutable
data class ExtendedColors(
    val snowWhite: Color,
    val deepOcean: Color,
    val skyBlue: Color,
    val nightBlue: Color,
    val dialogBackground: Color,
)

val lightCor: ColorScheme
    @Composable get() =
        lightColorScheme(
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
    @Composable get() =
        darkColorScheme(
            primary = dark_primary,
            onPrimary = dark_onPrimary,
            secondary = dark_secondary,
            onSecondary = dark_onSecondary,
            onTertiary = dark_onTertiary,
            background = dark_background,
            onBackground = dark_onBackground,
            surface = dark_surface,
            onSurface = dark_onSurface,
            surfaceVariant = dark_surfaceVariant,
            onSurfaceVariant = dark_onSurfaceVariant,
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

private val schemeIntro =
    lightColorScheme(
        background = light_primary,
        onBackground = light_primary,
    )

@Composable
fun PetJournalTheme(
    isDynamic: Boolean = false,
    isIntro: Boolean = false,
    ignoreAppTheme: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val (isDarkPref, isSystemThemePref) =
        if (!LocalInspectionMode.current && !ignoreAppTheme) {
            val getDarkModeUseCase: GetDarkModePreferenceUseCase = get()
            val getSystemThemeUseCase: GetSystemThemePreferenceUseCase = get()
            val darkPref by getDarkModeUseCase().collectAsState(initial = false)
            val systemPref by getSystemThemeUseCase().collectAsState(initial = true)
            Pair(darkPref, systemPref)
        } else {
            Pair(false, true)
        }

    val finalDarkTheme = if (isSystemThemePref) darkTheme else isDarkPref

    val colors =
        when {
            isDynamic && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (finalDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            finalDarkTheme -> if (!isIntro) DarkCor else schemeIntro
            else -> if (!isIntro) lightCor else schemeIntro
        }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
    )
}

@Preview
@Composable
fun PreviewColorTheme() {
    ColorSchemePreview(lightCor)
}

@Composable
fun ColorSchemePreview(lightCor: ColorScheme) {
    Column {
        with(lightCor) {
            ProcessColor("primary", primary)
            ProcessColor("onPrimary", onPrimary)
            ProcessColor("primaryContainer", primaryContainer)
            ProcessColor("onPrimaryContainer", onPrimaryContainer)
            ProcessColor("inversePrimary", inversePrimary)
            ProcessColor("secondary", secondary)
            ProcessColor("onSecondary", onSecondary)
            ProcessColor("secondaryContainer", secondaryContainer)
            ProcessColor("onSecondaryContainer", onSecondaryContainer)
            ProcessColor("tertiary", tertiary)
            ProcessColor("onTertiary", onTertiary)
            ProcessColor("tertiaryContainer", tertiaryContainer)
            ProcessColor("onTertiaryContainer", onTertiaryContainer)
            ProcessColor("background", background)
            ProcessColor("onBackground", onBackground)
            ProcessColor("surface", surface)
            ProcessColor("onSurface", onSurface)
            ProcessColor("surfaceVariant", surfaceVariant)
            ProcessColor("onSurfaceVariant", onSurfaceVariant)
            ProcessColor("surfaceTint", surfaceTint)
            ProcessColor("inverseSurface", inverseSurface)
            ProcessColor("inverseOnSurface", inverseOnSurface)
            ProcessColor("error", error)
            ProcessColor("onError", onError)
            ProcessColor("errorContainer", errorContainer)
            ProcessColor("onErrorContainer", onErrorContainer)
            ProcessColor("outline", outline)
            ProcessColor("outlineVariant", outlineVariant)
            ProcessColor("scrim", scrim)
        }
    }
}

@Composable
fun ProcessColor(
    name: String,
    color: Color,
) {
    Row {
        Row(modifier = Modifier.background(color)) {
            Text(
                text = "----",
                Modifier.background(color),
                color = color.copy(red = 1f),
            )
        }
        Text(
            text = name,
            Modifier.background(color),
            color = Color.Cyan,
        )
    }
}
