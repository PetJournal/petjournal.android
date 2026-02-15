package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.pet.CreatePetUseCase
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PetRegisterViewModelImpl(
    private val createPetUseCase: CreatePetUseCase,
) : PetRegisterViewModel() {
    private val _stateUi = MutableStateFlow<StateUI>(StateUI())
    override val stateUi: StateFlow<StateUI>
        get() {
            return _stateUi.asStateFlow()
        }

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        getPetList()
    }

    private fun getPetList() {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            _stateUi.value.pet?.let { pet ->
                val result = createPetUseCase.execute(pet)
                result.handleResult({
                    _taskState.value = TaskState.Idle
                }, {
                    _taskState.value = TaskState.Idle
                })
            }
        }
    }
}
