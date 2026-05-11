package com.soujunior.petjournal.ui.model

import java.util.UUID

data class Pets(
    val uuid: UUID = UUID.randomUUID(),
    val id: String? = null,
    val imageRes: String? = null,
    val name: String? = null,
)
