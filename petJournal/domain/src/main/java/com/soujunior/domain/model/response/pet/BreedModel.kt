package com.soujunior.domain.model.response.pet

import com.soujunior.domain.model.BreedDTO

data class BreedModel(
    val id: String? = null,
    val specieId: String? = null,
    val name: String? = null
)


fun List<BreedDTO>.toDomain(): List<BreedModel>{
return this.map { it.toDomain() }
}

fun BreedDTO.toDomain(): BreedModel{
    return BreedModel(
        id = this.id,
        specieId = this.specieId,
        name = this.name
    )
}