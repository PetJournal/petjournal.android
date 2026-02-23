package com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.pet.GetListPetUseCase
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PetListViewModelImpl(
    private val getPetListUseCase: GetListPetUseCase,
) : PetListViewModel() {
    private val _state = MutableStateFlow(State())
    override val state: StateFlow<State> = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents: Flow<ValidationEvent> get() = super.validationEvents

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        getPetList()
    }

    private fun getPetList() {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = getPetListUseCase.execute(Unit)
            result.handleResult({
                _state.update { currentState ->
                    currentState.copy(listPets = it)
                }
                _taskState.value = TaskState.Idle
            }, ::failed)
        }
    }

    override fun failed(exception: Throwable?) {
        exception?.message?.let { Log.e(TAG, it) }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }
}
