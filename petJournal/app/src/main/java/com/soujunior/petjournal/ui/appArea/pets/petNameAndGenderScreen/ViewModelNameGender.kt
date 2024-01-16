package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.lang.Exception

abstract class ViewModelNameGender : ViewModel(){
    abstract var state: NameGenderFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>
    abstract val name: StateFlow<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success() //o que a success vai retornar?
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: NameGenderFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun change(
        petName: String? = null,
        petGender: Char? = null
    )

}