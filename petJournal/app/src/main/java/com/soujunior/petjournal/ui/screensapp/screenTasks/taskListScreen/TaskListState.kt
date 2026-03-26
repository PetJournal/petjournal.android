package com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen

import com.soujunior.petjournal.ui.model.TaskData

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
