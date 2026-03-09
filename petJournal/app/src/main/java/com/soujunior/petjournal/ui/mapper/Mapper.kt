package com.soujunior.petjournal.ui.mapper

import androidx.compose.ui.graphics.Color
import com.soujunior.domain.model.PetModelV2
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.petjournal.ui.model.Pets
import com.soujunior.petjournal.ui.model.SelectableButtonInfo

object Mapper {
    fun PetModelV2.toPets(): Pets {
        return Pets(
            id = this.id,
            imageRes = this.image,
            name = this.petName,
        )
    }

    fun List<PetModelV2>.toPetsList(): List<Pets> {
        return this.map { it.toPets() }
    }

    fun String.toColor(): Int {
        var cleanHex = this.removePrefix("#")

        if (cleanHex.length == 6) {
            cleanHex = "FF$cleanHex"
        }
        return cleanHex.toLong(16).toInt()
    }

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
