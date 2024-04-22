package com.soujunior.petjournal.ui.screens_app.pets_screens.petBirthDateScreen

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

class BirthDateViewModelImpl(
    val validation: ValidationRepository,
    private val getPetInformationUseCase: GetPetInformationUseCase,
    private val updatePetInformationUseCase: UpdatePetInformationUseCase
) : BirthDateViewModel() {

    override var state by mutableStateOf(BirthDateFormState())
    override val validationEventChannel get() = Channel<ValidationEvent>()
    override val message: StateFlow<String> get() = _message
    private val _message = MutableStateFlow("")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState


    override fun success(petInformationModel: PetInformationModel) {
        state = state.copy(
            idPetInformation = petInformationModel.id,
            specie = petInformationModel.species ?: "",
            name = petInformationModel.name ?: "",
            gender = petInformationModel.gender ?: "",
            size = petInformationModel.size ?: "",
            race = petInformationModel.petRace ?: ""

        )
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

    override fun onEvent(event: BirthDateFormEvent) {
        when (event) {
            is BirthDateFormEvent.PetBirthDate -> change(petBirth = event.petBirth)
            is BirthDateFormEvent.IdPetInformation -> change(idPetInformation = event.idPetInformation)
            is BirthDateFormEvent.NextButton -> {
                change(petBirth = state.birth)
            }
        }
    }

    override fun enableButton(): Boolean {
        return state.birthError.isNullOrEmpty()
    }

    override fun change(petBirth: String?, idPetInformation: Long?) {
        when {
            petBirth != null -> {
                state = state.copy(birth = petBirth)
                val result = validation.validateDate(state.birth)
                state = if (result.success) state.copy(birthError = null)
                else state.copy(birthError = result.errorMessage)
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
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val petInformation = PetInformationModel(
                id = state.idPetInformation ?: 0L,
                species = state.specie,
                name = state.name,
                gender = state.gender,
                size = state.size,
                petRace = state.race,
                petAge = state.birth,
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

}