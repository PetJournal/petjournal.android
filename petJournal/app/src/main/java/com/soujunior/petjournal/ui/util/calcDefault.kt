package com.soujunior.petjournal.ui.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun calcDefault(sizeToMultiply: Int, quantity: Int): Dp {
    return (sizeToMultiply * quantity).dp
}