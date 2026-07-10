package com.soujunior.petjournal.ui.model

import com.soujunior.domain.model.PetModelV2
import com.soujunior.petjournal.ui.components.data.TaskType

data class TaskData(
    val id: String,
    val schedulerId: String = "",
    val title: String,
    val descriptionResumed: String,
    val descriptionCompleted: String,
    val startAt: String,
    val endAt: String = "",
    val type: TaskType,
    val pets: List<PetModelV2> = emptyList(),
)
