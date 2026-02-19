package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetModel
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

    init {
        _taskState.value = TaskState.Idle
    }

    override fun onEvent(event: CreatePetEvent) {
        when (event) {
            is CreatePetEvent.OnInputName -> {
                _stateUi.value = _stateUi.value.copy(petName = event.name)
            }
            is CreatePetEvent.OnInputImage -> {
                _stateUi.value = _stateUi.value.copy(petImage = event.image)
            }
            is CreatePetEvent.OnTypeSelected -> {
                _stateUi.value =
                    _stateUi.value.copy(
                        selectedAnimalType = event.type,
                        petRace = null,
                        petSize = null,
                        listRace = emptyList(),
                        listSize = emptyList(),
                    )
                fetchRace(_stateUi.value.convert(event.type))
                fetchSizes(_stateUi.value.convert(event.type))
            }
            is CreatePetEvent.OnInputRace -> {
                _stateUi.value = _stateUi.value.copy(petRace = event.breed)
            }
            is CreatePetEvent.OnInputSize -> {
                _stateUi.value = _stateUi.value.copy(petSize = event.size)
            }
            is CreatePetEvent.OnInputBirthday -> {
                _stateUi.value = _stateUi.value.copy(petBirthday = event.birthday)
            }
            is CreatePetEvent.OnInputSex -> {
                _stateUi.value = _stateUi.value.copy(petGender = event.sex)
            }
            is CreatePetEvent.OnInputCastrated -> {
                _stateUi.value = _stateUi.value.copy(petCastrated = event.isCastrated)
            }
            is CreatePetEvent.OnSubmit -> {
                createPet(stateUi.value.buildPetModel())
            }
        }
    }

    private fun fetchSizes(animalType: String) {
        viewModelScope.launch {
            _stateUi.value = _stateUi.value.copy(isLoadingSizes = true)
            val result = getListSizeUseCase.execute(animalType)

            result.handleResult(
                { sizeList ->
                    _stateUi.value =
                        _stateUi.value.copy(
                            isLoadingSizes = false,
                            listSizeOnly = sizeList.toList(),
                        )
                },
                { error ->
                    _stateUi.value =
                        _stateUi.value.copy(
                            isLoadingSizes = false,
                            showDialogError = true,
                            messageError = error?.message ?: "Erro desconhecido ao carregar tamanhos.",
                        )
                },
            )
        }
    }

    private fun fetchRace(animalType: String) {
        viewModelScope.launch {
            _stateUi.value = _stateUi.value.copy(isLoadingBreeds = true)
            val result = getListBreedUseCase.execute(animalType)
            result.handleResult(
                { breeds ->
                    _stateUi.value =
                        _stateUi.value.copy(
                            isLoadingBreeds = false,
                            listRaceOnly = breeds.toList(),
                        )
                },
                { error ->
                    _stateUi.value =
                        _stateUi.value.copy(
                            isLoadingSizes = false,
                            showDialogError = true,
                            messageError = error?.message ?: "Erro desconhecido ao carregar tamanhos.",
                        )
                },
            )
        }
    }

    private fun createPet(pet: PetModel) {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = createPetUseCase.execute(pet)
            result.handleResult(
                {
                    _taskState.value = TaskState.Idle
                    _stateUi.value = _stateUi.value.copy(showDialogSuccess = true)
                },
                {
                    _taskState.value = TaskState.Idle
                    _stateUi.value =
                        _stateUi.value.copy(
                            showDialogError = true,
                            messageError = it?.message.toString(),
                        )
                },
            )
        }
    }
}
