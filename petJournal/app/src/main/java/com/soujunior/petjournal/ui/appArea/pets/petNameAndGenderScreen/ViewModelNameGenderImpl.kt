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
            is NameGenderFormEvent.NextButton -> generic()
//            is NameGenderFormEvent.ReturnButton -> generic()
            else -> {}
        }
    }

    override fun enableButton(): Boolean {
        val petNameResult = validation.inputPetName(state.name)
        val petGenderResult = validation.inputPetGender(state.gender)
        return petNameResult.success && petGenderResult.success
    }

    override fun change(petName: String?, petGender: String?) {
        when{
            petName != null -> {
                state = state.copy(name = petName)
                val result = validation.inputPetName(state.name)
                state = if (hasError(result)) state.copy(nameError = result.errorMessage)
                else state.copy(nameError = null)

            }

            petGender != null -> {
                state = state.copy(gender = petGender)
                val result = validation.inputPetGender(state.gender)
                state = if(hasError(result)) state.copy(genderError = result.errorMessage)
                else state.copy(genderError = null)
            }
        }
    }
    fun generic(){
        Log.d("Teste", state.name )
        Log.d("Teste", state.gender )
    }
    private fun hasError(result: ValidationResult): Boolean {
        return listOf(result).any { !it.success }
    }
    private fun getData(){
//        viewModelScope.launch {
//            _taskState.value = TaskState.Loading
//            val result =
//        }
    }

}