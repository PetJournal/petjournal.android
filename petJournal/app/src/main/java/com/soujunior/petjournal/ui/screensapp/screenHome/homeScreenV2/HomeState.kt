package com.soujunior.petjournal.ui.screensapp.screenHome.homeScreenV2

import com.soujunior.petjournal.ui.components.data.TaskData
import com.soujunior.petjournal.ui.components.horizontalButtonList.TagOption

data class HomeState(
    val name: String = "",
    val getNameError: Boolean = false,
    val menuItems: List<TagOption> = emptyList(),
    val listTaskData: List<TaskData> = emptyList(),
)
