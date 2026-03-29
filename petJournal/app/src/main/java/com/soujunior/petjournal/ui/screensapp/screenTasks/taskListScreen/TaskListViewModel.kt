package com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class TaskListViewModel : ViewModel() {
    abstract val state: StateFlow<TaskListState>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(name: GuardianNameResponse)

    abstract fun failed(exception: Throwable?)

    abstract fun onEvent(event: TaskListEvent)
}

class FakeTaskListViewModel : TaskListViewModel() {
    override val state = MutableStateFlow(TaskListState(isLoading = true))
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()

    override fun success(name: GuardianNameResponse) {}

    override fun failed(exception: Throwable?) {}

    override fun onEvent(event: TaskListEvent) {}
}
