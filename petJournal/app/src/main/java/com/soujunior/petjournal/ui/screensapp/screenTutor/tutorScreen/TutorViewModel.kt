package com.soujunior.petjournal.ui.screensapp.screenTutor.tutorScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeTutorViewModel : TutorViewModel() {
    override val state = MutableStateFlow(TutorState(nameUser = "Carla Westervelt Fake"))
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()

    override fun success(name: GuardianNameResponse) {}

    override fun getGuardianName() {}

    override fun failed(exception: Throwable?) {}

    override fun logout() {}
}

abstract class TutorViewModel : ViewModel() {
    abstract val state: StateFlow<TutorState>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent> get() = validationEventChannel.receiveAsFlow()

    abstract fun success(name: GuardianNameResponse)

    abstract fun getGuardianName()

    abstract fun failed(exception: Throwable?)

    abstract fun logout()
}
