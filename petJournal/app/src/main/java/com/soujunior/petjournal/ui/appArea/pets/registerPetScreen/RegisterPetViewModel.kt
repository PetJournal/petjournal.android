package com.soujunior.petjournal.ui.appArea.pets.registerPetScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class RegisterPetViewModel : ViewModel() {
    abstract val visualizedScreen : StateFlow<Boolean>

    abstract val message: StateFlow<String>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(value: String)
    abstract fun failed(exception: Throwable?)
    abstract fun setWasViewed()
    abstract fun getWasViewed()
}