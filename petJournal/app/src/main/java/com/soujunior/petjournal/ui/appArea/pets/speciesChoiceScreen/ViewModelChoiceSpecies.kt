package com.soujunior.petjournal.ui.appArea.pets.speciesChoiceScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.mapper.PetInformationModel
import com.soujunior.domain.model.response.GuardianNameResponse
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ViewModelChoiceSpecies : ViewModel() {
    abstract var state: PetFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>
    abstract val name: StateFlow<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val idRoomPetInformation: StateFlow<Long>
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