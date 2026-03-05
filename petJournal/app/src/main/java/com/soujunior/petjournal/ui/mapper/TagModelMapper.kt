package com.soujunior.petjournal.ui.mapper

import androidx.compose.ui.graphics.Color
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.petjournal.ui.components.SelectableButtonInfo

object TagModelMapper {
    fun TagModel.uiModel(): SelectableButtonInfo {
        var cleanHex = this.color?.removePrefix("#")

        if (cleanHex?.length == 6) {
            cleanHex = "FF$cleanHex"
        }

        val color = cleanHex?.toLong(16)?.toInt()

        return SelectableButtonInfo(
            id = this.id,
            title = this.name ?: "",
            color =
                try {
                    color?.let { Color(it) } ?: Color.Black
                } catch (_: Exception) {
                    Color.Black
                },
        )
    }

    fun List<TagModel>.toUiModelList(): MutableList<SelectableButtonInfo> {
        return this.map { it.uiModel() }.toMutableList()
    }

    fun List<TagModel>.toListSelectableButtonInfo(): MutableList<SelectableButtonInfo> {
        return this.toUiModelList()
    }
}
