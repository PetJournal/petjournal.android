package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

sealed class RegisterTaskEvent {
    object ReloadListPet : RegisterTaskEvent()
}
