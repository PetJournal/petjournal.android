package com.soujunior.domain.model.response.tag

import com.google.gson.annotations.SerializedName

data class UpdatePetByIdDTO(
    @SerializedName("isSuccess") val isSuccess: Boolean? = null,
    @SerializedName("data") val data: TagDTO? = null,
)