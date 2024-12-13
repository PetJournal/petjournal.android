package com.soujunior.domain.model.response

data class PetInformationDeleted(
    val message: String? = null,
    val petId: Long = 0,
    val petApiId: String? = null
)
