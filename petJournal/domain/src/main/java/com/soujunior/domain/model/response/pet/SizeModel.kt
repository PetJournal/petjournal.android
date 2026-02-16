package com.soujunior.domain.model.response.pet

import com.soujunior.domain.model.SizeDTO

data class SizeModel(
    val id: String? = null,
    val name: String? = null
)

fun List<SizeDTO>.toDomain(): List<SizeModel>{
    return this.map { it.toDomain() }
}

fun SizeDTO.toDomain(): SizeModel{
    return SizeModel(
        id = this.id,
        name = this.name
    )
}