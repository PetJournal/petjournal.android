package com.soujunior.petjournal.ui.appArea.pets.petNameAndGenderScreen

import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

class ViewModelNameGenderImpl(): ViewModelNameGender() {
    override var state: NameGenderState
        get() = TODO("Not yet implemented")
        set(value) {}
    override val validationEventChannel: Channel<ValidationEvent>
        get() = TODO("Not yet implemented")
    override val message: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val taskState: StateFlow<TaskState>
        get() = TODO("Not yet implemented")
    override val name: StateFlow<String>
        get() = TODO("Not yet implemented")

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

    override fun change(petName: String?, petGender: String?) {
        TODO("Not yet implemented")
    }
}