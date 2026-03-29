package com.soujunior.petjournal.ui.screensapp.screenTasks.taskListScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskListViewModelImpl : TaskListViewModel() {
    private val _state = MutableStateFlow(TaskListState(isLoading = true))
    override val state: StateFlow<TaskListState> = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()

    override fun success(name: GuardianNameResponse) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun onEvent(event: TaskListEvent) {
        // Implement logic for UI events
    }
}
