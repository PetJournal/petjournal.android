package com.soujunior.petjournal.ui.appArea.pets.petBirthDateScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelBirthDateImpl(
    val validation: ValidationRepository
) : ViewModelBirthDate() {

    override var state by mutableStateOf(BirthDateFormState())
    override val validationEventChannel: Channel<ValidationEvent>
        get() = TODO("Not yet implemented")
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    private val _petBirth: MutableStateFlow<String> = MutableStateFlow("")
    override val petBirth: StateFlow<String> = _petBirth


    private var _petGender: MutableStateFlow<String> = MutableStateFlow("")
    override val petGender: StateFlow<String> = _petGender

    init {
       verifyPetGender()
    }

    /*Os metodos de Success e Failure vão ser utilizados
    quando houver a nova impl do dataclass*/
    override fun success() {
        TODO("teste")
    }

    override fun failed(exception: Throwable?) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    override fun onEvent(event: BirthDateFormEvent) {
        when (event) {
            is BirthDateFormEvent.PetBirthDate -> change(petBirth = event.petBirth)
            is BirthDateFormEvent.NextButton -> {
                change(petBirth = state.birth)
            }
        }
    }

    override fun enableButton(): Boolean {
        return state.birthError.isNullOrEmpty()
    }

    override fun verifyPetGender() {
        //Chamada ao Room para verificar qual o genero do pet

        _petGender.value = "Adotada"
    }

    override fun change(petBirth: String?) {
        when {
            petBirth != null -> {
                state = state.copy(birth = petBirth)
                val result = validation.validateDate(state.birth)
                state = if (result.success) state.copy(birthError = null)
                else state.copy(birthError = result.errorMessage)

            }
        }
    }

}