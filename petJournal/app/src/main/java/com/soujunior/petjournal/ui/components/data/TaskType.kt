package com.soujunior.petjournal.ui.components.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class TaskType(
    val id: String,
    val name: String,
    val color: Color = Color.Black,
    @DrawableRes val icon: Int? = null,
    @DrawableRes val iconVector: Int? = null,
)
