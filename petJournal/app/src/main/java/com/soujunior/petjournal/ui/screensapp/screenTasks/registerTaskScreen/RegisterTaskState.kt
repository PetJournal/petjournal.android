package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

import com.soujunior.domain.model.PetModel
import com.soujunior.petjournal.ui.components.data.TaskData
import com.soujunior.petjournal.ui.model.SelectableButtonInfo

data class RegisterTaskState(
    val nameUser: String = "",
    val isLoadingUserName: Boolean = false,
    val hasErrorOnNameUser: Boolean = false,
    val listTaskData: List<TaskData> = emptyList(),
    val hasErrorOnListTag: Boolean = false,
    val isLoadingListTag: Boolean = false,
    val listTag: MutableList<SelectableButtonInfo> = emptyList<SelectableButtonInfo>().toMutableList(),
    val isLoadingAll: Boolean = false,
    val taskName: String = "",
    val isLoadingTaskName: Boolean = false,
    val taskDescription: String = "",
    val isLoadingTaskDescription: Boolean = false,
    val isLoadingListPet: Boolean = false,
    val hasErrorOnListPets: Boolean = false,
    val listPets: List<PetModel> = emptyList(),
)
