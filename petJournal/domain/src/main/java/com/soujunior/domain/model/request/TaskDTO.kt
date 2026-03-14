package com.soujunior.domain.model.request

data class TaskDTO(
    val tagId: String,
    val title: String,
    val description: String,
    val note: String,
    val startAt: String,
    val endAt: String?,
    val daysOfWeek: List<Int>,
    val daysOfMonth: List<Int>,
    val daily: Boolean,
    val pets: List<String>,
)