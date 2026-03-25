package com.soujunior.domain.model.request

data class TaskDTO(
    val tagId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val note: String? = null,
    val startAt: String? = null,
    val endAt: String? = null,
    val daysOfWeek: List<Int>? = null,
    val pets: List<String>? = null
)