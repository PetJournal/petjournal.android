package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.NameGenderFormEvent
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ViewModelRaceAndSize: ViewModel() {
    abstract var state: RaceAndSizeFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>
    abstract val petName: StateFlow<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()


    abstract fun success()
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: NameGenderFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun change(
        petRace: String? = null,
        petSize: String? = null
    )
}