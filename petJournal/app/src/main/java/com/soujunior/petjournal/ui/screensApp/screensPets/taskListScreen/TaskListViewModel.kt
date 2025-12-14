package com.soujunior.petjournal.ui.screensApp.screensPets.taskListScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class TaskListViewModel : ViewModel() {
    abstract var state: TaskListState
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()
    abstract val taskState: StateFlow<TaskListState>

    abstract fun success(name: GuardianNameResponse)

    abstract fun failed(exception: Throwable?)

    abstract fun onEvent(event: TaskListEvent)
}
