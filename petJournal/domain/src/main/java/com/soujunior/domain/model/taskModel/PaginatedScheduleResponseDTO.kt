package com.soujunior.domain.model.taskModel

import com.google.gson.annotations.SerializedName
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.response.tag.TagDTO

data class PaginatedScheduleResponseDTO(
    @SerializedName("data")
    val data: List<ScheduleDataDTO>,

    @SerializedName("page")
    val page: Int,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("count")
    val count: Int
)

data class ScheduleDataDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("schedulerId")
    val schedulerId: String,

    @SerializedName("start")
    val start: String,

    @SerializedName("end")
    val end: String,

    @SerializedName("scheduler")
    val scheduler: SchedulerDTO
)

data class SchedulerDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("tagId")
    val tagId: String,

    @SerializedName("guardianId")
    val guardianId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("note")
    val note: String,

    @SerializedName("startAt")
    val startAt: String,

    @SerializedName("endAt")
    val endAt: String,

    @SerializedName("daysOfWeek")
    val daysOfWeek: List<Int>,

    @SerializedName("daysOfMonth")
    val daysOfMonth: List<Int>,

    @SerializedName("daily")
    val daily: Boolean,

    @SerializedName("tag")
    val tag: TagDTO,

    @SerializedName("pets")
    val pets: List<PetDetailsDTO>
)