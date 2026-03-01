package com.soujunior.domain.model.response.tag

import com.google.gson.annotations.SerializedName

data class TagDTO(
    @SerializedName("id") val id : String? = null,
    @SerializedName("guardianId") val guardianId : String? = null,
    @SerializedName("name") val name : String? = null,
    @SerializedName("color") val color : String? = null,
)

fun TagDTO.toDomain(): TagModel {
    return TagModel(
        id = this.id,
        guardianId = this.guardianId,
        name = this.name,
        color = this.color,
    )
}

fun List<TagDTO>.toDomain(): List<TagModel> {
    return this.map { it.toDomain() }
}