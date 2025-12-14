package com.soujunior.petjournal.ui.screensapp.screenspets.taskListScreen

sealed class TaskListEvent {
    object AddTaskButton : TaskListEvent()

    data class OnDateFilterChange(val dateFilter: DateFilter) : TaskListEvent()
}
