package com.soujunior.domain.model.request.taskModels

data class DailyTaskModel(
    val title: String,
    val description: String,
    val note : String = "",
    val startAt : String,
    val endAt : String,
    val tagId : String,
    val pets: List<String>
)
