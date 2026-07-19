package com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen

sealed class TaskListEvent {
    object AddTaskButton : TaskListEvent()

    data class OnDateFilterChange(val dateFilter: DateFilter) : TaskListEvent()

    object OnRefresh : TaskListEvent()

    data class OnDeleteOnlyThisTask(val id: String) : TaskListEvent()

    data class OnDeleteAllTheseTask(val schedulerId: String) : TaskListEvent()
}
