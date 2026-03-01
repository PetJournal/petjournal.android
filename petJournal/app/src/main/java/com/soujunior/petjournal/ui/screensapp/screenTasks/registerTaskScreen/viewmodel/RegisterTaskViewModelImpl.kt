package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel

import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskEvent
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskState
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

class RegisterTaskViewModelImpl : RegisterTaskViewModel() {
    override val state = MutableStateFlow(RegisterTaskState())

    init {
        getData()
    }

    private fun getData() {
//        todo: getTags
    }

    private fun getTags() {
    }

    override val validationEventChannel = Channel<ValidationEvent>()

    override fun onEvent(event: RegisterTaskEvent) {
        TODO("Not yet implemented")
    }

    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)
}
