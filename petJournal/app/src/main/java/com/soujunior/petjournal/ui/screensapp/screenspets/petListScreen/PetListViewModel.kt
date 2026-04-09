package com.soujunior.petjournal.ui.screensapp.screenspets.petListScreen

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

class FakePetListViewModel() : PetListViewModel() {
    override val state: StateFlow<State>
        get() {
            TODO()
        }
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun failed(exception: Throwable?) {}

    override fun deletePetById(id: String) {
        TODO("Not yet implemented")
    }

    override fun reload() {}
}

abstract class PetListViewModel : ViewModel() {
    abstract val state: StateFlow<State>

    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val taskState: StateFlow<TaskState>

    abstract fun failed(exception: Throwable?)

    abstract fun deletePetById(id: String)

    abstract fun reload()
}
