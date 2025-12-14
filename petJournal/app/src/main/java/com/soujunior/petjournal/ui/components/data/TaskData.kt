package com.soujunior.petjournal.ui.components.data

data class TaskData(
    val id: String,
    val title: String,
    val descriptionResumed: String,
    val descriptionCompleted: String,
    val startAt: String,
    val endAt: String = "",
    val type: TaskType,
    val pets: List<FakePetData> = emptyList(),
)
