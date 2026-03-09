package com.soujunior.domain.model.response.tag

import com.google.gson.annotations.SerializedName

data class TagDTO(
    @SerializedName("id") val id : String? = null,
    @SerializedName("guardianId") val guardianId : String? = null,
    @SerializedName("name") val name : String? = null,
    @SerializedName("color") val color : String? = null,
)