package com.soujunior.domain.model.request.taskModels

data class WeeklyTaskModel(
    val title: String,
    val description: String,
    val note : String = "",
    val start_at : String,
    val end_at : String,
    val days_of_week : List<Int>,
    val tag_id : String,
    val pets: List<String>
)
