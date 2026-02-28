package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

import com.soujunior.domain.model.PetModel
import com.soujunior.petjournal.ui.components.data.TaskData
import com.soujunior.petjournal.ui.components.horizontalButtonList.TagOption

data class RegisterTaskState(
    val nameUser: String = "",
    val isLoadingUserName: Boolean = false,
    val hasErrorOnNameUser: Boolean = false,
    val listTaskData: List<TaskData> = emptyList(),
    val menuItems: List<TagOption> = emptyList(),
    val isLoadingListPet: Boolean = false,
    val hasErrorOnListPets: Boolean = false,
    val listPets: List<PetModel> = emptyList(),
    val isLoadingListTag: Boolean = false,
    val hasErrorOnListTag: Boolean = false,
    val listTag: List<TagOption> = emptyList(),
)
