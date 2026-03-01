package com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.task.GetListTagUseCase
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskEvent
import com.soujunior.petjournal.ui.screensapp.screenTasks.registerTaskScreen.RegisterTaskState
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterTaskViewModelImpl(
    private val getListTagCase: GetListTagUseCase,
) : RegisterTaskViewModel() {
    private val _state = MutableStateFlow(RegisterTaskState())
    override val state: MutableStateFlow<RegisterTaskState>
        get() = _state

    init {
        getData()
    }

    private fun getData() {
        getTags()
    }

    private fun getTags() {
        _state.update { it.copy(isLoadingListTag = true) }
        viewModelScope.launch {
            val result = getListTagCase.execute(Unit)
            result.handleResult({ value ->
                _state.update {
                    it.copy(isLoadingListTag = false, listTag = _state.value.convert(value))
                }
            }, {
                _state.update { it.copy(isLoadingListTag = false, hasErrorOnListTag = true) }
            })
        }
    }

    override val validationEventChannel = Channel<ValidationEvent>()

    override fun onEvent(event: RegisterTaskEvent) {
        TODO("Not yet implemented")
    }

    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)
}
