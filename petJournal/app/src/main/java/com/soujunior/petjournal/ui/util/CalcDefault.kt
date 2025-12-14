package com.soujunior.petjournal.ui.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun CalcDefault(
    sizeToMultiply: Int,
    quantity: Int,
): Dp {
    return (sizeToMultiply * quantity).dp
}
