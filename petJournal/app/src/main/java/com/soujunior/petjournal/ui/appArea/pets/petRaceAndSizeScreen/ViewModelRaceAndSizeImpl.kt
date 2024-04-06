package com.soujunior.petjournal.ui.appArea.pets.petRaceAndSizeScreen

import com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen.NameGenderFormEvent
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

class ViewModelRaceAndSizeImpl(
    override var state: RaceAndSizeFormState,
    override val validationEventChannel: Channel<ValidationEvent>,
    override val message: StateFlow<String>,
    override val taskState: StateFlow<TaskState>,
    override val petName: StateFlow<String>
) : ViewModelRaceAndSize() {
    override fun success() {
        TODO("Not yet implemented")
    }

    override fun failed(exception: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: NameGenderFormEvent) {
        TODO("Not yet implemented")
    }

    override fun enableButton(): Boolean {
        TODO("Not yet implemented")
    }

    override fun change(petRace: String?, petSize: String?) {
        TODO("Not yet implemented")
    }
}