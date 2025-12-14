package com.soujunior.petjournal.ui.screensApp.screensPets.taskListScreen

sealed class TaskListEvent {
    object AddTaskButton : TaskListEvent()

    data class OnDateFilterChange(val dateFilter: DateFilter) : TaskListEvent()
}
