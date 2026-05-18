package com.soujunior.domain.model.taskModel

import com.google.gson.annotations.SerializedName

data class PaginatedNextEventsResponseDTO(
    @SerializedName("nextEvents")
    val nextEvents: List<ScheduleDataDTO> = emptyList(),

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("limit")
    val limit: Int? = null,

    @SerializedName("totalPages")
    val totalPages: Int? = null
)

data class PaginatedNextEventsResponseModel(
    val nextEvents: List<ScheduleDataModel>,
    val page: Int? = null,
    val limit: Int? = null,
    val totalPages: Int? = null
)
