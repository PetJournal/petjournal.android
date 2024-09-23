package com.soujunior.petjournal.ui.screens_app.screens_pets.speciesChoiceScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeChoiceSpeciesViewModel : ViewModelChoiceSpecies() {
    override var state: PetFormState
        get() = PetFormState()
        set(value) {}
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()
    override fun success(name: GuardianNameResponse) {}

    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun failed(exception: Throwable?) { /* No-op */
    }

    override fun onEvent(event: PetFormEvent) {}


    override fun enableButton(): Boolean = true


    override fun change(specieSelected: String?, specieWritten: String?) {}

    override fun savePetInformation(specie: String) {}

}

abstract class ViewModelChoiceSpecies : ViewModel() {
    abstract var state: PetFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val taskState: StateFlow<TaskState>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(name: GuardianNameResponse)
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: PetFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun change(
        specieSelected: String? = null,
        specieWritten: String? = null
    )

    abstract fun savePetInformation(specie: String)
}