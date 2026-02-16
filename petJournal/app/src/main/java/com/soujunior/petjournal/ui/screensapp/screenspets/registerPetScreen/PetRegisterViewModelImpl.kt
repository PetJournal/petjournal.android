package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.use_case.pet.CreatePetUseCase
import com.soujunior.domain.use_case.pet.GetListBreedUseCase
import com.soujunior.domain.use_case.pet.GetListSizeUseCase
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PetRegisterViewModelImpl(
    private val createPetUseCase: CreatePetUseCase,
    private val getListBreedUseCase: GetListBreedUseCase,
    private val getListSizeUseCase: GetListSizeUseCase,
) : PetRegisterViewModel() {
    private val _stateUi = MutableStateFlow(StateUI())
    override val stateUi: StateFlow<StateUI>
        get() {
            return _stateUi.asStateFlow()
        }

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    override fun onEvent(event: CreatePetEvent) {
        when (event) {
            is CreatePetEvent.OnInputName -> {
                _stateUi.value = _stateUi.value.copy(petName = event.name)
            }
            is CreatePetEvent.OnInputBreed -> {
                _stateUi.value = _stateUi.value.copy(petBreed = event.breed)
            }
            is CreatePetEvent.OnSubmit -> {
                createPet()
            }
        }
    }

    init {
        _taskState.value = TaskState.Idle
    }

    private fun createPet() {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            _stateUi.value.pet?.let { pet ->
                val result = createPetUseCase.execute(pet)
                result.handleResult({
                    _taskState.value = TaskState.Idle
                    _stateUi.value = _stateUi.value.copy(showDialogSuccess = true)
                }, {
                    _taskState.value = TaskState.Idle
                    _stateUi.value =
                        _stateUi.value.copy(
                            showDialogError = true,
                            messageError = it?.message.toString(),
                        )
                })
            }
        }
    }
}
