package com.soujunior.petjournal.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.soujunior.petjournal.R

val RobotoSemiBold = Font(R.font.roboto_semi_bold)
val RobotoRegular = Font(R.font.roboto_regular)
val RobotoMedium = Font(R.font.roboto_medium)
val RobotoLight = Font(R.font.roboto_light)
val RobotoBold = Font(R.font.roboto_bold)

val Typography =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = FontFamily(RobotoSemiBold),
                fontSize = 32.sp,
                letterSpacing = 0.sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = FontFamily(RobotoSemiBold),
                fontSize = 28.sp,
                letterSpacing = 0.sp,
            ),
        displaySmall =
            TextStyle(
                fontFamily = FontFamily(RobotoSemiBold),
                fontSize = 24.sp,
                letterSpacing = 0.sp,
            ),
        headlineLarge =
            TextStyle(
                fontFamily = FontFamily(RobotoMedium),
                fontSize = 24.sp,
                letterSpacing = 0.sp,
            ),
        headlineMedium =
            TextStyle(
                fontFamily = FontFamily(RobotoMedium),
                fontSize = 20.sp,
                letterSpacing = 0.sp,
            ),
        headlineSmall =
            TextStyle(
                fontFamily = FontFamily(RobotoMedium),
                fontSize = 18.sp,
                letterSpacing = 0.sp,
            ),
        titleLarge =
            TextStyle(
                fontFamily = FontFamily(RobotoSemiBold),
                fontSize = 20.sp,
                letterSpacing = 0.sp,
            ),
        titleMedium =
            TextStyle(
                fontFamily = FontFamily(RobotoMedium),
                fontSize = 16.sp,
                letterSpacing = 0.15.sp,
            ),
        titleSmall =
            TextStyle(
                fontFamily = FontFamily(RobotoMedium),
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
            ),
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily(RobotoRegular),
                fontSize = 16.sp,
                letterSpacing = 0.5.sp,
            ),
        bodyMedium =
            TextStyle(
                fontFamily = FontFamily(RobotoRegular),
                fontSize = 14.sp,
                letterSpacing = 0.25.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = FontFamily(RobotoRegular),
                fontSize = 12.sp,
                letterSpacing = 0.4.sp,
            ),
        labelLarge =
            TextStyle(
                fontFamily = FontFamily(RobotoMedium),
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
            ),
        labelMedium =
            TextStyle(
                fontFamily = FontFamily(RobotoRegular),
                fontSize = 12.sp,
                letterSpacing = 0.5.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = FontFamily(RobotoLight),
                fontSize = 11.sp,
                letterSpacing = 0.5.sp,
            ),
    )
