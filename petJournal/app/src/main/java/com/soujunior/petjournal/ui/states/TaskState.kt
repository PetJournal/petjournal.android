package com.soujunior.petjournal.ui.states

sealed class TaskState {
    object Idle: TaskState()
    object Loading: TaskState()
}