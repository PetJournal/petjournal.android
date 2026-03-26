package com.soujunior.domain.model.taskModel

import com.google.gson.annotations.SerializedName
import com.soujunior.domain.model.PetDetailsDTO
import com.soujunior.domain.model.PetModelV2
import com.soujunior.domain.model.response.tag.TagDTO
import com.soujunior.domain.model.response.tag.TagModel

data class PaginatedScheduleResponseDTO(
    @SerializedName("data")
    val data: List<ScheduleDataDTO> = emptyList(),

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("limit")
    val limit: Int? = null,

    @SerializedName("count")
    val count: Int? = null
)

data class ScheduleDataDTO(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("schedulerId")
    val schedulerId: String? = null,

    @SerializedName("start")
    val start: String? = null,

    @SerializedName("end")
    val end: String? = null,

    @SerializedName("scheduler")
    val scheduler: SchedulerDTO
)

data class SchedulerDTO(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("tagId")
    val tagId: String? = null,

    @SerializedName("guardianId")
    val guardianId: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("note")
    val note: String? = null,

    @SerializedName("startAt")
    val startAt: String? = null,

    @SerializedName("endAt")
    val endAt: String? = null,

    @SerializedName("daysOfWeek")
    val daysOfWeek: List<Int>? = null,

    @SerializedName("daysOfMonth")
    val daysOfMonth: List<Int>? = null,

    @SerializedName("daily")
    val daily: Boolean? = null,

    @SerializedName("tag")
    val tag: TagDTO,

    @SerializedName("pets")
    val pets: List<PetDetailsDTO>
)

data class SchedulerModel(
    val id: String? = null,
    val tagId: String? = null,
    val guardianId: String? = null,
    val title: String? = null,
    val description: String? = null,
    val note: String? = null,
    val startAt: String? = null,
    val endAt: String? = null,
    val daysOfWeek: List<Int>? = null,
    val daysOfMonth: List<Int>? = null,
    val daily: Boolean? = null,
    val tag: TagModel,
    val pets: List<PetModelV2>
)

data class ScheduleDataModel(
    val id: String? = null,
    val schedulerId: String? = null,
    val start: String? = null,
    val end: String? = null,
    val scheduler: SchedulerModel
)

data class PaginatedScheduleResponseModel(
    val data: List<ScheduleDataModel>,
    val page: Int? = null,
    val limit: Int? = null,
    val count: Int? = null
)
