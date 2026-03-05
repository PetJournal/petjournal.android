package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

import com.soujunior.domain.model.PetModel
import com.soujunior.petjournal.ui.components.SelectableButtonInfo
import com.soujunior.petjournal.ui.components.data.TaskData

data class RegisterTaskState(
    val nameUser: String = "",
    val isLoadingUserName: Boolean = false,
    val hasErrorOnNameUser: Boolean = false,
    val listTaskData: List<TaskData> = emptyList(),
    val isLoadingListPet: Boolean = false,
    val hasErrorOnListPets: Boolean = false,
    val listPets: List<PetModel> = emptyList(),
    val hasErrorOnListTag: Boolean = false,
    val isLoadingListTag: Boolean = false,
    val listTag: MutableList<SelectableButtonInfo> = emptyList<SelectableButtonInfo>().toMutableList(),
)
