package com.soujunior.petjournal.ui.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.ui.graphics.Color
import com.soujunior.domain.model.PetModelV2
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.model.taskModel.ScheduleDataModel
import com.soujunior.petjournal.ui.components.data.TaskType
import com.soujunior.petjournal.ui.model.Pets
import com.soujunior.petjournal.ui.model.SelectableButtonInfo
import com.soujunior.petjournal.ui.model.TagOption
import com.soujunior.petjournal.ui.model.TaskData

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

    fun String?.toComposeColor(fallback: Color = Color.Gray): Color {
        return try {
            val validStr = this ?: return fallback
            var cleanHex = validStr.removePrefix("#")
            if (cleanHex.length == 6) {
                cleanHex = "FF$cleanHex"
            }
            Color(cleanHex.toLong(16))
        } catch (e: Exception) {
            fallback
        }
    }

    fun TagModel.toTagOption(): TagOption {
        return TagOption(
            id = this.id.orEmpty(),
            label = this.name.orEmpty(),
            icon = Icons.Default.Apps,
            color = this.color.toComposeColor(Color.Gray),
        )
    }

    fun String?.toFormattedDate(): String {
        if (this.isNullOrBlank()) return ""
        return try {
            val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.getDefault())
            inputFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")
            val date = inputFormat.parse(this)

            val outputFormat = java.text.SimpleDateFormat("dd 'de' MMMM 'às' HH:mm'h'", java.util.Locale("pt", "BR"))
            outputFormat.timeZone = java.util.TimeZone.getDefault()
            date?.let { outputFormat.format(it) } ?: this
        } catch (e: Exception) {
            this
        }
    }

    fun List<ScheduleDataModel>.toTaskData(): List<TaskData> {
        return this.map {
            TaskData(
                id = it.id ?: "",
                title = it.scheduler.title ?: "",
                descriptionResumed = it.scheduler.description ?: "",
                descriptionCompleted = it.scheduler.note ?: "",
                startAt = it.start.toFormattedDate(),
                endAt = "--------",
                type =
                    TaskType(
                        id = it.scheduler.tagId ?: "",
                        name = it.scheduler.tag.name ?: "",
                        color = it.scheduler.tag.color.toComposeColor(Color(0xFF000000)),
                        iconVector = null,
                    ),
                pets = it.scheduler.pets,
            )
        }
    }

    fun PaginatedScheduleResponseModel.toListOfTaskData(): List<TaskData> {
        return this.data.toTaskData()
    }
}
