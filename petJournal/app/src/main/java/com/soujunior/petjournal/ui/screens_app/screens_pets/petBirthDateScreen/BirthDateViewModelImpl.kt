package com.soujunior.petjournal.ui.screens_app.screens_pets.petBirthDateScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.pet.CreatePetInformationApiUseCase
import com.soujunior.domain.use_case.pet.GetPetInformationUseCase
import com.soujunior.domain.use_case.pet.UpdatePetInformationUseCase
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.Constants.ERROR_MESSAGE
import com.soujunior.petjournal.ui.util.Constants.SUCCESS_MESSAGE
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class BirthDateViewModelImpl(
    val validation: ValidationRepository,
    private val getPetInformationUseCase: GetPetInformationUseCase,
    private val updatePetInformationUseCase: UpdatePetInformationUseCase,
    private val createPetInformationApiUseCase: CreatePetInformationApiUseCase
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
        _message.value = SUCCESS_MESSAGE
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
        _message.value = ERROR_MESSAGE
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
        viewModelScope.launch {
            val petInformation = PetInformationModel(
                id = state.idPetInformation ?: 0L,
                species = state.specie,
                name = state.name,
                gender = state.gender,
                size = state.size,
                petRace = state.race,
                petAge = formatToIso8601(state.birth),
                guardianId = 1
            )

            val result = updatePetInformationUseCase.execute(petInformation)
            result.handleResult(::successPetUpdate, ::failed)
        }
    }

    override fun createPetInformation() {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val petInformation = PetInformationModel(
                id = state.idPetInformation ?: 0L,
                species = state.specie,
                name = state.name,
                gender = state.gender,
                size = state.size,
                petRace = state.race,
                petAge = formatToIso8601(state.birth),
                guardianId = 1
            )
                val result = createPetInformationApiUseCase.execute(petInformation)
                _message.value = result.success.data.toString()
                result.handleResult(::successPetUpdate, ::failed)
                Log.i(TAG, result.success.data.toString())
            _taskState.value = TaskState.Idle
        }
    }

    override fun successPetUpdate(unit: Unit) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
        _message.value = SUCCESS_MESSAGE
    }



    private fun formatToIso8601(date: String): String {
        val dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        val dateF = LocalDate.parse(date, dateFormatter)
        val localDateTime = dateF.atStartOfDay()
        return localDateTime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME)
    }

}