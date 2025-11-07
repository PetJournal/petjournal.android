package com.soujunior.domain.model.request.taskModels

data class PontualTaskModel(
    val title: String,
    val description: String,
    val note : String = "",
    val scheduledAt : String,
    val tag_id : String,
    val pets: List<String>
)
