package com.soujunior.petjournal.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TagOption(
    val id: String,
    val label: String,
    val icon: ImageVector,
    val color: Color,
)
