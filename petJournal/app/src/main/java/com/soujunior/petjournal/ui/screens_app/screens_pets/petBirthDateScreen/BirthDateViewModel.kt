package com.soujunior.petjournal.ui.screens_app.screens_pets.petBirthDateScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.PetInformationModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeBirthDateViewModel : BirthDateViewModel() {
    override var state: BirthDateFormState
        get() = BirthDateFormState()
        set(value) {}
    override val validationEventChannel = Channel<ValidationEvent>()
    override val message = MutableStateFlow("Mensagem de Teste")
    override val validationEvents = emptyFlow<ValidationEvent>()
    override fun success(petInformationModel: PetInformationModel) {}


    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun failed(exception: Throwable?) { /* No-op */ }

    override fun onEvent(event: BirthDateFormEvent) {}


    override fun enableButton(): Boolean = true
    override fun change(petBirth: String?, idPetInformation: Long?, petCastration: Boolean?) {}

    override fun getPetInformation(id: Long) {}
    override fun updatePetInformation() {}
    override fun createPetInformation() {}

    override fun successPetUpdate(unit: Unit) {}

}

abstract class BirthDateViewModel : ViewModel() {
    abstract var state: BirthDateFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(petInformationModel: PetInformationModel)
    abstract fun failed(exception: Throwable?)
    abstract fun onEvent(event: BirthDateFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun change(
        petBirth: String? = null,
        idPetInformation: Long? = null,
        petCastration: Boolean? = null
    )

    abstract fun getPetInformation(id: Long)
    abstract fun updatePetInformation()
    abstract fun createPetInformation()

    abstract fun successPetUpdate(unit: Unit)
}