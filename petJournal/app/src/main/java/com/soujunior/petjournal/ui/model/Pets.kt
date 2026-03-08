package com.soujunior.petjournal.ui.model

import androidx.compose.ui.graphics.painter.Painter
import com.soujunior.domain.model.PetModel

data class Pets(
    val id: Int = 0,
    val imageRes: Painter? = null,
    val name: String? = null,
)

fun PetModel.toPets(): Pets {
    return Pets()
}

// data class PetModel(
//    val id: Long = 0,
//    val petName: String? = null,
//    val species: String? = null,
//    val petRace: String? = null,
//    val size: String? = null,
//    val weight: String? = null,
//    val dateOfBirth: String? = null,
//    val gender: String? = null,
//    val castrated: Boolean? = null,
//    val image: String? = null,
//    val guardianId: Int? = null,
//    val petAge: String? = null
// )
