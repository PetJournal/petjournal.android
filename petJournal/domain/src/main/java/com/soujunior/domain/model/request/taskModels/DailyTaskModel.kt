package com.soujunior.domain.model.request.taskModels

data class DailyTaskModel(
    val title: String,
    val description: String,
    val note : String = "",
    val start_at : String,
    val end_at : String,
    val tag_id : String,
    val pets: List<String>
)
