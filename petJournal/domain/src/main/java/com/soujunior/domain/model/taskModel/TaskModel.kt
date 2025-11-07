package com.soujunior.domain.model.taskModel

import androidx.annotation.DrawableRes
import com.soujunior.domain.model.PetInformationModel


// TODO: Não editar isso enquanto o backend não fornecer um modelo para as tasks.
//data class TaskModel(
//    val id: String,
//    val titulo: String,
//    val descricaoResumida: String,
//    val descricaoCompleta: String,
//    val dataHora: String,
//    val tipo: TaskType,
//    val pets: List<PetData> = emptyList()
//)

data class TaskTagModel(
    val id: String,
    val name: String,
    val color: String,
    @DrawableRes val icone: Int? = null, // Recebe um vetor drawable
    @DrawableRes val iconeVector: Int? = null // Recebe um drawable vector
)


// TODO: Criar objetos para os requests pontuais/diarios/semanais/mensais
data class TaskModel(
    val id: String,
    val description: String,
    val note : String = "",
    val tagId : String,
    val scheduledAt : String = "", //se for pontual
    val startAt : String = "", //se for diario/
    val endAt: String = "",
    val daysOfWeek : List<Int> = emptyList<Int>(),
    val daysOfMonth : List<Int> = emptyList<Int>(),
    val pets: List<String>
)
