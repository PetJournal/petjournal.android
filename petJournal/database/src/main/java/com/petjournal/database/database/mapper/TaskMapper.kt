package com.petjournal.database.database.mapper

import com.petjournal.database.database.entity.TaskEntity
import com.soujunior.domain.mapper.Mapper.toDomain
import com.soujunior.domain.model.response.tag.TagModel
import com.soujunior.domain.model.taskModel.ScheduleDataModel
import com.soujunior.domain.model.taskModel.SchedulerModel

object TaskMapper {
    fun TaskEntity.toDomainModel(): ScheduleDataModel {
        return ScheduleDataModel(
            id = id,
            schedulerId = schedulerId,
            start = start,
            end = end,
            scheduler = scheduler?.toDomain() ?: SchedulerModel(
                id = schedulerId,
                title = title,
                description = description,
                note = note,
                startAt = start,
                endAt = end,
                daily = isRecurrent && recurrenceType == "DAILY",
                daysOfWeek = daysOfWeek?.split(",")?.mapNotNull { it.toIntOrNull() },
                daysOfMonth = daysOfMonth?.split(",")?.mapNotNull { it.toIntOrNull() },
                tag = TagModel(id = tagId ?: "", name = "", color = "", guardianId = ""),
                pets = emptyList()
            )
        )
    }
}
