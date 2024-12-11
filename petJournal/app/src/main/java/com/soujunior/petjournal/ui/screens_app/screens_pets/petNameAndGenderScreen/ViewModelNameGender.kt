package com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeNameAndGenderViewModel : ViewModelNameGender() {
    override var state: NameGenderFormState
        get() = NameGenderFormState()
        set(value) {}
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun failed(exception: Throwable?) { /* No-op */
    }

    override fun onEvent(event: NameGenderFormEvent) {}
    override fun enableButton(): Boolean = true
    override fun change(petName: String?, petGender: String?, idPetInformation: Long?) {}

    override fun getPetInformation(id: Long) {}
    override fun updatePetInformation() {}

    override fun successPetUpdate(unit: Unit) {}

}

abstract class ViewModelNameGender : ViewModel() {
    abstract var state: NameGenderFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val taskState: StateFlow<TaskState>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: NameGenderFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun change(
        petName: String? = null,
        petGender: String? = null,
        idPetInformation: Long? = null
    )

    abstract fun getPetInformation(id: Long)
    abstract fun updatePetInformation()
    abstract fun successPetUpdate(unit: Unit)
}