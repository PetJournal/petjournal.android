package com.soujunior.petjournal.ui.components.bottomSheet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Representa o objeto raiz da tarefa/evento.
 * Note o uso de Long para IDs numéricos (padrão de banco de dados)
 * e @SerialName para mapear o snake_case do backend.
 */
@Serializable
data class TaskRemoteEntity(
    val id: Long,
    val tag: TagRemoteEntity,
    val title: String,
    val description: String,
    val note: String? = null,
    val time: String,
    @SerialName("end_at")
    val endAt: String,
    @SerialName("days_of_month")
    val daysOfMonth: List<Int>,
    val pets: List<PetRemoteEntity>,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class TagRemoteEntity(
    val id: String,
    val name: String,
    val color: String,
)

@Serializable
data class PetRemoteEntity(
    val id: Long,
    val image: String,
)
