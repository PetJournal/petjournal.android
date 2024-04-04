package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen

import android.util.Log
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

class ViewModelNameGenderImpl(
    val validation: ValidationRepository,
    private val getPetInformationUseCase: GetPetInformationUseCase,
    private val updatePetInformationUseCase: UpdatePetInformationUseCase
) : ViewModelNameGender() {

    override var state by mutableStateOf(NameGenderFormState())
    override val validationEventChannel get() = Channel<ValidationEvent>()
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    init {
        //getPetInformation(state.idPetInformation!!)
    }

    override fun success(petInformation: PetInformationModel) {
        state = state.copy(specie = petInformation.species ?: "")
        state = state.copy(idPetInformation = petInformation.id ?: 0L)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun onEvent(event: NameGenderFormEvent) {
        when (event) {
            is NameGenderFormEvent.PetName -> change(petName = event.petName)
            is NameGenderFormEvent.PetGender -> change(petGender = event.petGender)
            is NameGenderFormEvent.IdPetInformation -> change(idPetInformation = event.idPetInformation)
            is NameGenderFormEvent.NextButton -> {
                change(petName = state.name)
                change(petGender = state.gender)
            }

            else -> {}
        }
    }

    override fun enableButton(): Boolean {
        return state.nameError.isNullOrEmpty() && state.genderError.isNullOrEmpty()
    }

    override fun change(petName: String?, petGender: String?, idPetInformation: Long?) {
        when {
            petName != null -> {
                state = state.copy(name = petName)
                val result = validation.inputPetName(state.name)
                state = if (result.success) state.copy(nameError = null)
                else state.copy(nameError = result.errorMessage)

            }

            petGender != null -> {
                state = state.copy(gender = petGender)
                val result = validation.inputPetGender(state.gender)
                state = if (result.success) state.copy(genderError = null)
                else state.copy(genderError = result.errorMessage)
            }

            idPetInformation != null -> {
                state = state.copy(idPetInformation = idPetInformation)
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
                guardianId = 1
            )

            val result = updatePetInformationUseCase.execute(petInformation)
            Log.i("tt", result.isFailure.toString())
           // result.handleResult(::success, ::failed)
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
        _taskState.value = TaskState.Idle


    }

    fun generic() {
        Log.d("Teste", state.name)
        Log.d("Teste", state.gender)
        Log.d("Teste", state.nameError.toString())
        Log.d("Teste", state.genderError.toString())
    }

    private fun getData() {
        /*
        viewModelScope.launch {
            _taskState.value = TaskState.Loading
            val result = getGuardianNameUseCase.execute(Unit)
            state = state.copy(name = result.success.data.firstName)
        }

         */
    }

}