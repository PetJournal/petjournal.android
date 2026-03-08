package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen

sealed class RegisterTaskEvent {
    object ReloadListPet : RegisterTaskEvent()

    class OnUpdateTag(val id: String, val name: String, val color: String) : RegisterTaskEvent()

    class OnCreateTag(val name: String, val color: String) : RegisterTaskEvent()

    class OnDeleteTag(val id: String) : RegisterTaskEvent()

    class OnName(val name: String) : RegisterTaskEvent()

    class OnDescription(val text: String) : RegisterTaskEvent()
}
