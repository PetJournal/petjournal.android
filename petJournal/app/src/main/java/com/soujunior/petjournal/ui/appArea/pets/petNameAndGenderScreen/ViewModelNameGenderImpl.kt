package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.util.ValidationResult
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelNameGenderImpl(
//    val getPetSpeciesUseCase: GetPetSpecieUseCase,
    val validation: ValidationRepository
): ViewModelNameGender() {

    override var state by mutableStateOf(NameGenderFormState())
    override val validationEventChannel: Channel<ValidationEvent>
        get() = TODO("Not yet implemented")
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState

    private val _petSpecie: MutableStateFlow<String> = MutableStateFlow("")
    override val petSpecie: StateFlow<String> = _petSpecie

//    init {
//        getData()
//    }

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

    override fun onEvent(event: NameGenderFormEvent) {
        when (event) {
            is NameGenderFormEvent.PetName -> change(petName  = event.petName)
            is NameGenderFormEvent.PetGender -> change(petGender = event.petGender)
            is NameGenderFormEvent.NextButton -> {
                change(petName = state.name)
                change(petGender = state.gender)
            }
            is NameGenderFormEvent.ReturnButton -> generic()
            else -> {}
        }
    }

    override fun enableButton(): Boolean {
        return state.nameError.isNullOrEmpty() && state.genderError.isNullOrEmpty()
    }

    override fun change(petName: String?, petGender: String?) {
        when{
            petName != null -> {
                state = state.copy(name = petName)
                val result = validation.inputPetName(state.name)
                state = if (result.success) state.copy(nameError = null)
                else state.copy(nameError = result.errorMessage)

            }

            petGender != null -> {
                state = state.copy(gender = petGender)
                val result = validation.inputPetGender(state.gender)
                state = if(result.success) state.copy(genderError = null)
                else state.copy(genderError = result.errorMessage)
            }
        }
    }
    fun generic(){
        Log.d("Teste", state.name)
        Log.d("Teste", state.gender)
        Log.d("Teste", state.nameError.toString())
        Log.d("Teste", state.genderError.toString())
    }

    private fun getData(){
//        viewModelScope.launch {
//            _taskState.value = TaskState.Loading
//            val result =
//        }
    }

}