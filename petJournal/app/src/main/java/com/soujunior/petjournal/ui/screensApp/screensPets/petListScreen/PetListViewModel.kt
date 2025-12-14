package com.soujunior.petjournal.ui.screensApp.screensPets.petListScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakePetListViewModel : PetListViewModel() {
    override val message = MutableStateFlow("Mensagem de Teste")
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun success() {
    }

    override fun failed(exception: Throwable?) {
    }
}

abstract class PetListViewModel : ViewModel() {
    abstract val message: StateFlow<String>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val taskState: StateFlow<TaskState>

    abstract fun success()

    abstract fun failed(exception: Throwable?)
}
