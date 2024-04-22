package com.soujunior.petjournal.ui.screens_app.screens_pets.petNameAndGenderScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ViewModelNameGender : ViewModel() {
    abstract var state: NameGenderFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val taskState: StateFlow<TaskState>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(petInformation: PetInformationModel)
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