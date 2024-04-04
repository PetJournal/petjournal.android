package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.pet.SavePetInformationUseCase
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class ViewModelChoiceSpeciesImpl(
    private val getGuardianNameUseCase: GetGuardianNameUseCase,
    private val validation: ValidationRepository,
    private val savePetInformationUseCase: SavePetInformationUseCase
) : ViewModelChoiceSpecies() {
    override var state by mutableStateOf(PetFormState())
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents: Flow<ValidationEvent>
        get() = super.validationEvents

    init {
        getData()
    }

    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }

    private fun setIdPetInformation(id: Long) {
        state = state.copy(idRoomPetInformation = id)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun success(name: GuardianNameResponse) {
        state = state.copy(name = name.firstName)
        viewModelScope.launch {
            _taskState.value = TaskState.Idle
        }
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun enableButton(): Boolean {
        val speciesResult = validation.inputSpecieType(state.specie)
        return speciesResult.success
    }

    private fun generic() {
        Log.e(TAG, "Clicado!")
    }

    override fun onEvent(event: PetFormEvent) {
        when (event) {
            is PetFormEvent.SpecieChosen -> change(specieSelected = event.specieSelected)
            is PetFormEvent.OtherSpecie -> change(specieWritten = event.specieWritten)
            is PetFormEvent.NextButton -> generic()
            is PetFormEvent.ReturnButton -> generic()
            is PetFormEvent.OtherButton -> generic()
        }
    }

    override fun change(
        specieSelected: String?,
        specieWritten: String?
    ) {
        when {
            specieSelected != null -> {
                state = state.copy(specie = specieSelected)
                validation.inputSpecieType(state.specie)
            }

            specieWritten != null -> {
                state = state.copy(specie = specieWritten)
                val result = validation.inputSpecieType(state.specie)
                state = if (hasError(result)) state.copy(specieError = result.errorMessage)
                else state.copy(specieError = null)
            }
        }
    }

    override fun savePetInformation(specie: String) {

        val petInformation = PetInformationModel(
            id = 0,
            species = specie
        )
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = savePetInformationUseCase.execute(petInformation)
            result.handleResult(::setIdPetInformation, ::failed)
            _taskState.value = TaskState.Idle
        }
    }

    private fun getData() {
        viewModelScope.launch {
            _taskState.value = TaskState.Loading
            val result = getGuardianNameUseCase.execute(Unit)
            result.handleResult(::success, ::failed)
        }
    }
}