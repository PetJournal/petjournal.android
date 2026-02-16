package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.PetModel
import com.soujunior.domain.model.response.pet.BreedModel
import com.soujunior.domain.model.response.pet.SizeModel
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class StateUI(
    val pet: PetModel? = null,
    val showDialogSuccess: Boolean = false,
    val showDialogError: Boolean = false,
    val messageError: String? = null,
    val petName: String? = null,
    val petBreed: String? = null,
    val petSize: String? = null,
    val listBreed: List<BreedModel> = emptyList(),
    val listSize: List<SizeModel> = emptyList(),
    val listAnimalType: List<String> = emptyList(),
)

sealed class CreatePetEvent {
    data class OnInputName(val name: String) : CreatePetEvent()

    data class OnInputBreed(val breed: String) : CreatePetEvent()

    object OnSubmit : CreatePetEvent()
}

class FakePetRegisterViewModel() : PetRegisterViewModel() {
    override val stateUi: StateFlow<StateUI> get() {
        TODO()
    }
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun onEvent(event: CreatePetEvent) {}
}

abstract class PetRegisterViewModel : ViewModel() {
    abstract val stateUi: StateFlow<StateUI>
    abstract val taskState: StateFlow<TaskState>

    abstract fun onEvent(event: CreatePetEvent)
}
