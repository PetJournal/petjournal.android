package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.domain.repository.ValidationRepository
import com.soujunior.domain.use_case.pet.GetAllPetInformationUseCase
import com.soujunior.domain.use_case.pet.GetPetInformationUseCase
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelRegisteredPetsImpl(
    val validation: ValidationRepository,
    private val getAllPetInformationUseCase: GetAllPetInformationUseCase,
) : ViewModelRegisteredPets() {

    override val validationEventChannel get() = Channel<ValidationEvent>()

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> get() = _taskState

    var registeredPets by mutableStateOf<List<PetInformationModel>>(emptyList())

    override fun success(petList: List<PetInformationModel>) {
        registeredPets = petList
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    override fun failed(exception: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: RegisteredPetFormEvent) {
        TODO("Not yet implemented")
    }

    override fun getAllPetInformation() {
        viewModelScope.launch {
            _taskState.value = TaskState.Loading
            val result = getAllPetInformationUseCase.execute(Unit)
            result.handleResult(::success, ::failed)
            _taskState.value = TaskState.Idle
        }
    }
}