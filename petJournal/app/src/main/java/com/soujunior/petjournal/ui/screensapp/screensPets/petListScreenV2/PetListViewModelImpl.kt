package com.soujunior.petjournal.ui.screensapp.screensPets.petListScreenV2

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.pet.DeletePetByIdUseCase
import com.soujunior.domain.use_case.pet.GetListPetUseCaseV1
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
    private val getPetListUseCase: GetListPetUseCaseV1,
    private val deletePetByIdUseCase: DeletePetByIdUseCase,
) : PetListViewModel() {
    private val _state = MutableStateFlow(State())
    override val state: StateFlow<State> = _state.asStateFlow()

    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents: Flow<ValidationEvent> get() = super.validationEvents

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        getPetList(forceRequest = false, isSilent = false)
    }

    private fun getPetList(
        forceRequest: Boolean = false,
        isSilent: Boolean = false,
    ) {
        if (!isSilent) {
            _taskState.value = TaskState.Loading
        }

        viewModelScope.launch {
            val result = getPetListUseCase.execute(forceRequest)
            result.handleResult({
                _state.update { currentState ->
                    currentState.copy(listPets = it)
                }
                _taskState.value = TaskState.Idle
            }, ::failed)
        }
    }

    override fun onResume() {
        getPetList(forceRequest = false, isSilent = true)
    }

    override fun reload() {
        getPetList(forceRequest = true, isSilent = false)
    }

    override fun failed(exception: Throwable?) {
        exception?.message?.let {
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun deletePetById(id: String) {
        viewModelScope.launch {
            val result = deletePetByIdUseCase.execute(id)
            result.handleResult(
                success = {
                    _state.update {
                        it.copy(
                            listPets = it.listPets.filter { it.idPet != id },
                        )
                    }
                },
                error = {
                },
            )
        }
    }
}
