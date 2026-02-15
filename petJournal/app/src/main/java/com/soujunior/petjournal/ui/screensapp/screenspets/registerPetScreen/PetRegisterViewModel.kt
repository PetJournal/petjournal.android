package com.soujunior.petjournal.ui.screensapp.screenspets.registerPetScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.PetModel
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class StateUI(
    val pet: PetModel? = null,
)

class FakePetRegisterViewModel() : PetRegisterViewModel() {
    override val stateUi: StateFlow<StateUI>
        get() {
            TODO()
        }
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)
}

abstract class PetRegisterViewModel : ViewModel() {
    abstract val stateUi: StateFlow<StateUI>

    abstract val taskState: StateFlow<TaskState>
}
