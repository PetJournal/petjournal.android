package com.soujunior.petjournal.ui.appArea.pets.petBirthScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ViewModelBirth : ViewModel(){
    abstract var state: BirthFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>
    abstract val petBirth: StateFlow<String>
    abstract val petGender: StateFlow<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success()
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: BirthFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun verifyPetGender(): Boolean
    abstract fun change(
        petBirth: String? = null,
    )

}