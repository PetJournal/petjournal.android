package com.soujunior.petjournal.ui.model

import androidx.compose.ui.graphics.Color
import com.soujunior.domain.model.PetModelV2
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.domain.model.taskModel.ScheduleDataModel
import com.soujunior.petjournal.ui.components.data.TaskType

data class TaskData(
    val id: String,
    val title: String,
    val descriptionResumed: String,
    val descriptionCompleted: String,
    val startAt: String,
    val endAt: String = "",
    val type: TaskType,
    val pets: List<PetModelV2> = emptyList(),
)

fun List<ScheduleDataModel>.toTaskData(): List<TaskData> {
    return this.map {
        TaskData(
            id = it.id ?: "",
            title = it.scheduler.title ?: "",
            descriptionResumed = it.scheduler.description ?: "",
            descriptionCompleted = it.scheduler.note ?: "",
            startAt = it.start ?: "",
            endAt = it.end ?: "",
            type =
                TaskType(
                    id = it.scheduler.tagId ?: "",
                    name = it.scheduler.tag.name ?: "",
                    color = it.scheduler.tag.color.toSafeColor(),
                    iconVector = null,
                ),
            pets = it.scheduler.pets,
        )
    }
}

fun PaginatedScheduleResponseModel.toListOfTaskData(): List<TaskData> {
    return this.data.toTaskData()
}

fun String?.toSafeColor(): Color {
    if (this.isNullOrBlank() || !this.startsWith("#")) {
        return Color.Black
    }
    return try {
        var hexString = this.removePrefix("#")

        if (hexString.length == 6) {
            hexString = "FF$hexString"
        }

        Color(hexString.toLong(16))
    } catch (e: Exception) {
        Color.Black
    }
}
