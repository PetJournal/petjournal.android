package com.soujunior.petjournal.ui.screens_app.screens_pets.introRegisterPetScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow

class FakeIntroRegisterPetViewModel : IntroRegisterPetViewModel() {
    override val name = MutableStateFlow<String?>("Nome de Teste")
    override val visualizedScreen = MutableStateFlow(false)
    override val message = MutableStateFlow("Mensagem de Teste")
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun success() { /* No-op */ }
    override fun failed(exception: Throwable?) { /* No-op */ }
    override fun setWasViewed() { /* No-op */ }
    override fun getName() { /* No-op */ }
    override fun getWasViewed() { /* No-op */ }
}

abstract class IntroRegisterPetViewModel : ViewModel() {
    abstract val name: StateFlow<String?>
    abstract val visualizedScreen: StateFlow<Boolean>

    abstract val message: StateFlow<String>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val taskState: StateFlow<TaskState>

    abstract fun success()
    abstract fun failed(exception: Throwable?)
    abstract fun setWasViewed()
    abstract fun getName()
    abstract fun getWasViewed()
}