package com.soujunior.petjournal.ui.model

import androidx.compose.ui.graphics.Color

sealed interface TagAction {
    data class Create(val name: String, val color: Color) : TagAction

    data class Update(val id: String, val name: String, val color: Color) : TagAction

    data class Delete(val id: String) : TagAction
}
