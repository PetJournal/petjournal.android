package com.soujunior.petjournal.ui.theme

import androidx.compose.material.Typography
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
    h1 = TextStyle(
        fontFamily = FontFamily(FredokaSemiBold),
        fontSize = 30.sp,
        letterSpacing = 1.5.sp
    ),
    h2 = TextStyle(
        fontFamily = FontFamily(FredokaSemiBold),
        fontSize = 25.sp,
        letterSpacing = 0.5.sp
    ),
    h3 = TextStyle(
        fontFamily = FontFamily(FredokaSemiBold),
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontFamily = FontFamily(FredokaMedium),
        fontSize = 15.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = FontFamily(FredokaMedium),
        fontSize = 10.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = FontFamily(FredokaMedium),
        fontSize = 5.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = FontFamily(FredokaMedium),
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(FredokaRegular),
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily(FredokaBold),
        fontSize = 18.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily(FredokaLight),
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontFamily = FontFamily(FredokaSemiBold),
        fontSize = 10.sp,
        letterSpacing = 1.5.sp
    )
)