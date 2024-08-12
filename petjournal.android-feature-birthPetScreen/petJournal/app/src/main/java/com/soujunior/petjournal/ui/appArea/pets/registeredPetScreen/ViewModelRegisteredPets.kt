package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen.RaceSizeFormEvent
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ViewModelRegisteredPets : ViewModel() {
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val taskState: StateFlow<TaskState>
    abstract var registeredPets : List<PetInformationModel>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(petList: List<PetInformationModel>)
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: RegisteredPetFormEvent)
    abstract fun getAllPetInformation()
}