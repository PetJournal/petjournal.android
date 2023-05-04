package com.soujunior.petjournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PetJournalTheme(
    isDynamic: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = notificationBarColor(isDarkTheme = darkTheme)
    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content
    )
}

@Composable
fun SplashTheme(
    isDynamic: Boolean = false,
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = notificationBarColor(isDarkTheme = darkTheme)
    MaterialTheme(
        colors = colors,
        typography = Typography,
        content = content,

        )
}