package com.soujunior.domain.model.request.taskModels

data class MonthlyTaskModel (
    val title : String,
    val description: String,
    val note : String = "",
    val tag_id : String,
    val time : String,
    val end_at: String,
    val days_of_month : List<Int>,
    val pets: List<String>
)
