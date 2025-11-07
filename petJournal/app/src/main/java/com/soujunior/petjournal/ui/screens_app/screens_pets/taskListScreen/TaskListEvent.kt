package com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen

sealed class TaskListEvent {
    object AddTaskButton : TaskListEvent()
    data class onDateFilterChange(val dateFilter: DateFilter) : TaskListEvent()
}
