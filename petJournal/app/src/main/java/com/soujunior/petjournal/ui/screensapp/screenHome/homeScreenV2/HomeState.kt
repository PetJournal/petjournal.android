package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.taskModel.PaginatedScheduleResponseModel
import com.soujunior.petjournal.ui.model.TagOption
import com.soujunior.petjournal.ui.model.TaskData

data class HomeState(
    val nameUser: String = "",
    val isLoadingListTask: Boolean = false,
    val isLoadingUserName: Boolean = false,
    val hasErrorOnNameUser: Boolean = false,
    val listTaskData: List<TaskData>? = null,
    val listScheduled: PaginatedScheduleResponseModel? = null,
    val menuItems: List<TagOption> = emptyList(),
    val isLoadingListPet: Boolean = false,
    val hasErrorOnListPets: Boolean = false,
    val listPets: List<PetModel> = emptyList(),
    val isLoadingListTag: Boolean = false,
    val hasErrorOnListTag: Boolean = false,
    val listTag: List<TagOption> = emptyList(),
)
