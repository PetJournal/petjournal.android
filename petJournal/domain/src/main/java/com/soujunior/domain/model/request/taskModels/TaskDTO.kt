package com.soujunior.domain.model.request.taskModels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class TaskDTO(
    @Json(name = "tagId") val tagId: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "note") val note: String? = null,
    @Json(name = "startAt") val startAt: String? = null,
    @Json(name = "endAt") val endAt: String? = null,
    @Json(name = "daysOfWeek") val daysOfWeek: List<Int>? = null,
    @Json(name = "pets") val pets: List<String>? = null
)