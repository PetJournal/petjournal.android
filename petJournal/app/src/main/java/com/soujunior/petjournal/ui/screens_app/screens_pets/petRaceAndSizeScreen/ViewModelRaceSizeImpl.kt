package com.soujunior.petjournal.ui.screens_app.screens_pets.petRaceAndSizeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.request.PetRaceItemModel
import com.soujunior.domain.model.request.PetSizeItemModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.pet.GetListPetRacesUseCase
import com.soujunior.domain.use_case.pet.GetListPetSizesUseCase
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
    private val updatePetInformationUseCase: UpdatePetInformationUseCase,
    private val getListPetSizesUseCase: GetListPetSizesUseCase,
    private val getListPetRacesUseCase: GetListPetRacesUseCase
) : ViewModelRaceSize() {

    override var state by mutableStateOf(RaceSizeFormState())
    override val validationEventChannel get() = Channel<ValidationEvent>()
    override val message: StateFlow<String> get() = _message
    private val _message = MutableStateFlow("")

    private val _isTextFiledOthersVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isTextFiledOthersVisible: StateFlow<Boolean> get() = _isTextFiledOthersVisible

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> get() = _taskState
    override val shouldScrollToTop = MutableStateFlow(false)

    override fun success(petInformationModel: PetInformationModel) {
        state = state.copy(
            specie = petInformationModel.species ?: "", idPetInformation = petInformationModel.id,
            name = petInformationModel.name ?: "", gender = petInformationModel.gender ?: ""
        )


        requestGetListRaces()
        requestGetListSizes()

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
        _message.value = "Sucesso"
    }

    override fun successGetPetSizes(listPetSizes: List<PetSizeItemModel>) {
        state = state.copy(listSizes = listPetSizes)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
        _message.value = "Sucesso"
    }

    override fun successGetPetRaces(listPetRaces: List<PetRaceItemModel>) {
        val listPetRaceFormat = mutableListOf<PetRaceItemModel>()

        listPetRaces.forEach { item ->
            val modifiedItem = if (item.name.startsWith("Outra raça")) {
                item.copy(name = "Outro")
            } else {
                item
            }
            listPetRaceFormat.add(modifiedItem)
        }
        state = state.copy(listRace = listPetRaceFormat)

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
        _message.value = "Sucesso"
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
        _message.value = "Error"
    }

    override fun onEvent(event: RaceSizeFormEvent) {
        when (event) {
            is RaceSizeFormEvent.ScrollToTop -> {
                shouldScrollToTop.value = event.scrollToTop
            }

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

        return if (enableRace()) {
            if (_isTextFiledOthersVisible.value) {
                state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty() && state.raceOthersError.isNullOrEmpty()
            } else {
                state.raceError.isNullOrEmpty() && state.sizeError.isNullOrEmpty()
            }
        } else {
            state.sizeError.isNullOrEmpty()
        }

    }

    override fun enableRaceOthers(): Boolean {
        val raceOtherResult = validation.validateDropDownRaceOthers(state.race)
        _isTextFiledOthersVisible.value = raceOtherResult.success
        return raceOtherResult.success
    }

    override fun enableRace(): Boolean {
        return state.specie == "Cat" || state.specie == "Dog"
    }

    override fun change(petRace: String?, petSize: String?, petRaceOthers: String?) {
        when {
            petSize != null -> {
                state = state.copy(size = petSize)
                val result = validation.validateDropdown(state.size, state.listSizes)
                state = if (result.success) state.copy(sizeError = null)
                else state.copy(sizeError = result.errorMessage)

            }

            petRace != null -> {
                state = state.copy(race = petRace)
                val result = validation.validateDropDownPetRace(state.race, state.listRace)
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
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
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
        _message.value = "Sucesso"
    }

    override fun requestGetListSizes() {
        _taskState.value = TaskState.Loading
        if (state.specie == "Cat" || state.specie == "Dog") {
            viewModelScope.launch {
                val result = getListPetSizesUseCase.execute(state.specie)
                result.handleResult(::successGetPetSizes, ::failed)
                _taskState.value = TaskState.Idle
            }
        }

    }

    override fun requestGetListRaces() {
        _taskState.value = TaskState.Loading
        if (state.specie == "Cat" || state.specie == "Dog") {
            viewModelScope.launch {
                val result = getListPetRacesUseCase.execute(state.specie)
                result.handleResult(::successGetPetRaces, ::failed)
                _taskState.value = TaskState.Idle
            }
        }
    }

}