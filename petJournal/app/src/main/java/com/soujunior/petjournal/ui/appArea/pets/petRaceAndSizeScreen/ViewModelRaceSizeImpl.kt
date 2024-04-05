package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.pet.GetPetInformationUseCase
import com.soujunior.domain.use_case.pet.UpdatePetInformationUseCase
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelRaceSizeImpl(
    val validation: ValidationRepository,
    private val getPetInformationUseCase: GetPetInformationUseCase,
    private val updatePetInformationUseCase: UpdatePetInformationUseCase
) : ViewModelRaceSize() {

    override var state by mutableStateOf(RaceSizeFormState())
    override val validationEventChannel get() = Channel<ValidationEvent>()
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")

    private val _isTextFiledOthersVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isTextFiledOthersVisible: StateFlow<Boolean> get() = _isTextFiledOthersVisible

    private val _sizeList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    override val sizeList: StateFlow<List<String>> get()= _sizeList

    private val _raceList: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    override val raceList: StateFlow<List<String>> get() = _raceList

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> get() = _taskState

    init {
        getData()

    }

    override fun success(petInformationModel: PetInformationModel) {
        state = state.copy(specie = petInformationModel.species ?: "")
        state = state.copy(idPetInformation = petInformationModel.id)
        state = state.copy(name = petInformationModel.name ?: "")
        state = state.copy(gender = petInformationModel.gender ?: "")
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun onEvent(event: RaceSizeFormEvent) {
        when (event) {
            is RaceSizeFormEvent.PetRace -> {
                change(petRace = event.petRace)
            }

            is RaceSizeFormEvent.PetSize -> change(petSize = event.petSize)
            is RaceSizeFormEvent.PetRaceOthers -> change(petRaceOthers = event.petRaceOthers)
            is RaceSizeFormEvent.NextButton -> {
                change(petRace = state.race)
                change(petSize = state.size)
                if (_isTextFiledOthersVisible.value) {
                    change(petRaceOthers = state.raceOthers)
                }
            }

            else -> {}
        }
    }

    override fun enableButton(): Boolean {

        return if (_isTextFiledOthersVisible.value) {
            state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty() && state.raceOthersError.isNullOrEmpty()
        } else {
            state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty()
        }

    }

    override fun enableRaceOthers(): Boolean {
        val raceOtherResult = validation.validateDropDownRaceOthers(state.race)
        _isTextFiledOthersVisible.value = raceOtherResult.success
        return raceOtherResult.success
    }

    override fun change(petRace: String?, petSize: String?, petRaceOthers: String?) {
        when {
            petSize != null -> {
                state = state.copy(size = petSize)
                val result = validation.validateDropdown(state.size, sizeList.value)
                state = if (result.success) state.copy(sizeError = null)
                else state.copy(sizeError = result.errorMessage)

            }

            petRace != null -> {
                state = state.copy(race = petRace)
                val result = validation.validateDropdown(state.race, raceList.value)
                state = if (result.success) state.copy(raceError = null)
                else state.copy(raceError = result.errorMessage)

            }

            petRaceOthers != null -> {
                state = state.copy(raceOthers = petRaceOthers)
                val result = validation.inputPetName(state.raceOthers)
                state = if (result.success) state.copy(raceOthersError = null)
                else state.copy(raceOthersError = result.errorMessage)

            }
        }
    }

    override fun getPetInformation(id: Long) {
        viewModelScope.launch {
            _taskState.value = TaskState.Loading
            val result = getPetInformationUseCase.execute(id)
            result.handleResult(::success, ::failed)
            _taskState.value = TaskState.Idle

        }
    }

    override fun updatePetInformation() {

        val petRace = if (state.race.lowercase() == "outro") state.raceOthers else state.race

        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val petInformation = PetInformationModel(
                id = state.idPetInformation ?: 0L,
                species = state.specie,
                name = state.name,
                gender = state.gender,
                size = state.size,
                petRace = petRace,
                guardianId = 1
            )

            val result = updatePetInformationUseCase.execute(petInformation)

            result.handleResult(::successPetUpdate, ::failed)
            _taskState.value = TaskState.Idle
        }
    }

    override fun successPetUpdate(unit: Unit) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }


    private fun getData() {
        _sizeList.value = listOf(
            "Pequeno (até 10kg)",
            "Médio (11 à 24kg)",
            "Grande (a cima de 25kg)"
        )
        _raceList.value = listOf(
            "Afghan Hound",
            "Affenpinscher",
            "Airedale Terrier",
            "Akita",
            "American Staffordshire Terrier",
            "Basenji",
            "Basset Hound",
            "Beagle",
            "Beagle Harrier",
            "Bearded Collie",
            "Bedlington Terrier",
            "Bichon Frisé",
            "Bloodhound",
            "Bobtail",
            "Boiadeiro Australiano",
            "Boiadeiro Bernês",
            "Border Collie",
            "Border Terrier",
            "Borzoi",
            "Boston Terrier",
            "Boxer",
            "Outro"
        )
    }

}