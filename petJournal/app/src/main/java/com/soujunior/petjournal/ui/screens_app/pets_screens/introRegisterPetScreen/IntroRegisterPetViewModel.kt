package com.soujunior.petjournal.ui.screens_app.pets_screens.introRegisterPetScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

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