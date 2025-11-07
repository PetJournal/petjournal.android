package com.soujunior.petjournal.ui.screens_app.screens_pets.taskListScreen

sealed class TaskListEvent {
    object addTaskButton : TaskListEvent()
    data class onDateFilterChange(val dateFilter: DateFilter) : TaskListEvent()
}
