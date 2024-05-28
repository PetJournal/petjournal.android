package com.soujunior.petjournal.ui.appArea.pets.registeredPetScreen

import com.soujunior.domain.model.PetInformationModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

class ViewModelRegisteredPetsImpl(
    override var state: RegisteredPetFormState,
    override val validationEventChannel: Channel<ValidationEvent>,
    override val message: StateFlow<String>,
    override val taskState: StateFlow<TaskState>
) : ViewModelRegisteredPets() {
    override fun success(petInformationModel: PetInformationModel) {
        TODO("Not yet implemented")
    }

    override fun failed(exception: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: RegisteredPetFormEvent) {
        TODO("Not yet implemented")
    }

    override fun enableButton(): Boolean {
        TODO("Not yet implemented")
    }

    override fun change() {
        TODO("Not yet implemented")
    }

    override fun getPetInformation(id: Long) {
        TODO("Not yet implemented")
    }
}