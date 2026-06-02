package com.soujunior.petjournal.ui.screensapp.screensPets.petListScreenV2

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.PetModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

data class State(
    val listPets: List<PetModel> = emptyList(),
)

class FakePetListViewModel : PetListViewModel() {
    private val mockPets =
        listOf(
            PetModel(idPet = "1", petName = "Rex", species = "Cachorro", image = null),
            PetModel(idPet = "2", petName = "Luna", species = "Gato", image = null),
            PetModel(idPet = "3", petName = "Bidu", species = "Cachorro", image = null),
            PetModel(idPet = "4", petName = "Pipoca", species = "Pássaro", image = null),
        )

    override val state = MutableStateFlow(State(listPets = mockPets))

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents: Flow<ValidationEvent> = emptyFlow()

    override fun failed(exception: Throwable?) {
    }

    override fun deletePetById(id: String) {
        val currentList = state.value.listPets.filter { it.idPet != id }
        state.value = state.value.copy(listPets = currentList)
    }

    override fun onResume() {
    }

    override fun reload() {
    }
}

abstract class PetListViewModel : ViewModel() {
    abstract val state: StateFlow<State>

    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val taskState: StateFlow<TaskState>

    abstract fun failed(exception: Throwable?)

    abstract fun deletePetById(id: String)

    abstract fun onResume()

    abstract fun reload()
}
