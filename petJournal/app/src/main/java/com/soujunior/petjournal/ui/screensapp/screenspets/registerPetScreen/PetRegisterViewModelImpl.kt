package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.use_case.information.GetListBreedUseCase
import com.soujunior.domain.use_case.pet.CreatePetUseCase
import com.soujunior.domain.use_case.pet.GetListSizeUseCase
import com.soujunior.domain.use_case.pet.GetPetByIdUseCase
import com.soujunior.domain.use_case.pet.UpdatePetInformationUseCase
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class PetRegisterViewModelImpl(
    private val savedStateHandle: SavedStateHandle,
    private val createPetUseCase: CreatePetUseCase,
    private val updatePetUseCase: UpdatePetInformationUseCase,
    private val getListBreedUseCase: GetListBreedUseCase,
    private val getListSizeUseCase: GetListSizeUseCase,
    private val getPetUseCase: GetPetByIdUseCase,
) : PetRegisterViewModel() {
    private val _stateUi = MutableStateFlow(StateUI())
    override val stateUi: StateFlow<StateUI>
        get() {
            return _stateUi.asStateFlow()
        }

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Loading)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        val idPet: String? = savedStateHandle.get<String>("idPet")
        if (idPet.isNullOrBlank()) {
            _taskState.value = TaskState.Idle
        } else {
            getPetById(idPet)
        }
    }

    private fun getPetById(idPet: String) {
        viewModelScope.launch {
            val result = getPetUseCase.execute(idPet)
            result.handleResult({ petDto ->
                fetchRace(_stateUi.value.convert(petDto.specie?.name.toString()), preselected = petDto.breed?.name)
                fetchSizes(_stateUi.value.convert(petDto.specie?.name.toString()), preselected = petDto.size?.name)

                _stateUi.update {
                    it.copy(
                        idPetSelected = idPet,
                        petName = petDto.petName,
                        petImage = petDto.image,
                        selectedAnimalType = petDto.specie?.name,
                        petBirthday = formatIsoToCompactDate(petDto.dateOfBirth),
                        petGender = petDto.gender.toString(),
                        petCastrated = petDto.castrated,
                    )
                }

                _taskState.value = TaskState.Idle
            }, {
            })
        }
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
                fetchRace(_stateUi.value.convert(event.type), null)
                fetchSizes(_stateUi.value.convert(event.type), null)
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
                if (validateRequiredFields()) {
                    if (_stateUi.value.idPetSelected.isNullOrBlank()) {
                        createPet(stateUi.value.buildPetModel())
                    } else {
                        stateUi.value.idPetSelected?.let { value ->
                            updatePet(id = value, stateUi.value.buildPetModel())
                        }
                    }
                }
            }

            is CreatePetEvent.OnCloseDialogError -> {
                _stateUi.update { it.copy(showDialogError = false) }
            }
            is CreatePetEvent.OnCleanState -> {
                cleanState()
            }
        }
    }

    private fun fetchSizes(
        animalType: String,
        preselected: String?,
    ) {
        viewModelScope.launch {
            _stateUi.value = _stateUi.value.copy(isLoadingSizes = true)
            val result = getListSizeUseCase.execute(animalType)

            result.handleResult(
                { sizeList ->
                    _stateUi.value =
                        _stateUi.value.copy(
                            isLoadingSizes = false,
                            petSize = preselected,
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

    private fun fetchRace(
        animalType: String,
        preselected: String?,
    ) {
        viewModelScope.launch {
            _stateUi.value = _stateUi.value.copy(isLoadingBreeds = true)
            val result = getListBreedUseCase.execute(animalType)
            result.handleResult(
                { breeds ->
                    _stateUi.value =
                        _stateUi.value.copy(
                            isLoadingBreeds = false,
                            petRace = preselected,
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
                    _stateUi.value =
                        _stateUi.value.copy(
                            showDialogSuccess = true,
                        )
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

    private fun updatePet(
        id: String,
        pet: PetModel,
    ) {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = updatePetUseCase.execute(Pair(id, pet))
            result.handleResult(
                success = {
                    _taskState.value = TaskState.Idle
                    _stateUi.value = _stateUi.value.copy(showDialogSuccess = true)
                },
                error = {
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

    private fun validateRequiredFields(): Boolean {
        val currentState = _stateUi.value
        val errorMessage =
            when {
                currentState.petImage.isNullOrBlank() -> "O pet não pode ficar sem foto."
                currentState.petName.isNullOrBlank() -> "O nome do pet não pode ficar em branco."
                currentState.petSize.isNullOrBlank() -> "Por favor, selecione o tamanho do pet."
                currentState.petRace.isNullOrBlank() -> "Por favor, selecione a raça do pet."
                currentState.petBirthday.isNullOrBlank() -> "A data de nascimento é obrigatória."
                currentState.petGender.isNullOrBlank() -> "Por favor, selecione o gênero do pet."
                currentState.petCastrated == null -> "Por favor, informe se o pet é castrado."
                else -> null
            }

        return if (errorMessage != null) {
            _stateUi.update {
                it.copy(
                    showDialogError = true,
                    messageError = errorMessage,
                )
            }
            false
        } else {
            true
        }
    }

    private fun cleanState() {
        _stateUi.update {
            it.copy(
                pet = null,
                idPetSelected = null,
                showDialogSuccess = false,
                showDialogError = false,
                isLoadingBreeds = false,
                isLoadingSizes = false,
                messageError = null,
                petImage = null,
                petName = null,
                petBirthday = null,
                petGender = null,
                petCastrated = null,
            )
        }
    }

    fun formatIsoToCompactDate(isoDate: String?): String {
        if (isoDate.isNullOrBlank()) return ""

        return try {
            val instant = Instant.parse(isoDate)

            val dateUtc = instant.atZone(ZoneOffset.UTC).toLocalDate()

            val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")

            dateUtc.format(formatter)
        } catch (e: Exception) {
            ""
        }
    }
}
