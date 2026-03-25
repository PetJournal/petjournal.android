package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskEvent
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskState
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class RegisterTaskViewModel : ViewModel() {
    abstract val taskState: StateFlow<TaskState>
    abstract val state: StateFlow<RegisterTaskState>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent> get() = validationEventChannel.receiveAsFlow()

    abstract fun onEvent(event: RegisterTaskEvent)

    abstract fun isFormComplete(): Boolean
}

class FakeRegisterTaskViewModel : RegisterTaskViewModel() {
    override val state = MutableStateFlow(RegisterTaskState())

    override val validationEventChannel = Channel<ValidationEvent>()

    override fun onEvent(event: RegisterTaskEvent) {
        TODO("Not yet implemented")
    }

    override fun isFormComplete(): Boolean {
        TODO("Not yet implemented")
    }

    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)
}
