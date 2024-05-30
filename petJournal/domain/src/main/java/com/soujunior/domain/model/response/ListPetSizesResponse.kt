package com.soujunior.domain.model.response

import com.soujunior.domain.model.request.PetSizeItemModel

data class ListPetSizesResponse (
    val listPetSizesResponse: List<PetSizeItemModel>
)