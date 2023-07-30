package com.soujunior.petjournal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R

val FredokaSemiBold = Font(R.font.fredoka_semi_bold)
val FredokaRegular = Font(R.font.fredoka_regular)
val FredokaMedium = Font(R.font.fredoka_medium)
val FredokaLight = Font(R.font.fredoka_light)
val FredokaBold = Font(R.font.fredoka_bold)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily(FredokaSemiBold),
        fontSize = 30.sp,
        letterSpacing = 1.5.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(FredokaSemiBold),
        fontSize = 25.sp,
        letterSpacing = 0.5.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(FredokaSemiBold),
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily(FredokaMedium),
        fontSize = 15.sp,
        letterSpacing = 0.25.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(FredokaMedium),
        fontSize = 10.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(FredokaMedium),
        fontSize = 5.sp,
        letterSpacing = 0.15.sp
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(FredokaMedium),
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    titleSmall  = TextStyle(
        fontFamily = FontFamily(FredokaBold),
        fontSize = 18.sp,
        letterSpacing = 1.25.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(FredokaLight),
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(FredokaSemiBold),
        fontSize = 10.sp,
        letterSpacing = 1.5.sp
    )
)