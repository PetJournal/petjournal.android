package com.soujunior.petjournal.ui.screensApp.screensPets.taskListScreen

import com.soujunior.petjournal.ui.components.data.TaskData

data class TaskListState(
    val tasks: List<TaskData> = emptyList(),
    val selectedDateFilter: DateFilter = DateFilter.DAILY,
    val error: String? = null,
)

enum class DateFilter {
    DAILY,
    WEEKLY,
    MONTHLY,
}
