package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.NameGenderFormEvent
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ViewModelRaceSize : ViewModel() {
    abstract var state: RaceSizeFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>
    abstract val petName: StateFlow<String>
    abstract val isSecondItemVisible: StateFlow<Boolean>
    abstract val sizeList: StateFlow<List<String>>
    abstract val raceList: StateFlow<List<String>>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success()
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: RaceSizeFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun change(
        petRace: String? = null,
        petSize: String? = null,
        petRaceOthers: String? = null
    )

}