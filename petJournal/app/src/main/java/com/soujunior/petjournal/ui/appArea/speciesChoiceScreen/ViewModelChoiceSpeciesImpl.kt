package com.soujunior.petjournal.ui.appArea.speciesChoiceScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.guardian.GetGuardianNameUseCase
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelChoiceSpeciesImpl(
    private val getGuardianNameUseCase: GetGuardianNameUseCase,
    private val validation: ValidationRepository
) : ViewModelChoiceSpecies() {
    override var state by mutableStateOf(PetFormState())
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val taskState: StateFlow<TaskState>
        get() = TODO("Not yet implemented")
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents: Flow<ValidationEvent>
        get() = super.validationEvents
    override val name: StateFlow<GuardianNameResponse> get() = _name
    private val _name = MutableStateFlow(GuardianNameResponse("", ""))

    init {
        getData()
    }

    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }

    fun updateName(newName: GuardianNameResponse) {
        _name.value = newName
    }

    override fun success(name: GuardianNameResponse) {
        updateName(name)
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {

    }

    override fun enableButton(): Boolean {
        val speciesResult = validation.inputSpecieType(state.specie)
        return speciesResult.success
    }

    fun generic() {
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
                /*state = state.copy(name = name)
                val nameResult = validation.validateName(state.name)
                state = if (hasError(nameResult)) state.copy(nameError = nameResult.errorMessage)
                else state.copy(nameError = null)*/
                Log.i(TAG,"-> specieSelected")
                state = state.copy(specie = specieSelected)
                val result = validation.inputSpecieType(state.specie)
                //state = if (hasError(result)) state.copy(specieError = result.errorMessage)
            }

            specieWritten != null -> {
                Log.i(TAG,"-> specieWritten")
                state = state.copy(specie = specieWritten)
                val result = validation.inputSpecieType(state.specie)
                state = if (hasError(result)) state.copy(specieError = result.errorMessage)
                else state.copy(specieError = null)
            }
        }
    }

    override fun getData() {
        viewModelScope.launch {
            val result = getGuardianNameUseCase.execute(Unit)
            result.handleResult(::success, ::failed)
        }
    }
}