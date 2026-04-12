package com.soujunior.petjournal.ui.screensapp.accountmanager.changePasswordScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ChangePasswordViewModel : ViewModel() {
    abstract var state: ChangePasswordFormState
    abstract val validationEventChannel: Channel<ValidationEvent>

    abstract val message: StateFlow<String>

    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val taskState: StateFlow<TaskState>

    abstract fun success(result: String)

    abstract fun failed(exception: Throwable?)

    abstract fun submitNewPassword()

    abstract fun onEvent(event: ChangePasswordFormEvent)

    abstract fun disconnectOtherDevices()

    abstract fun enableButton(): Boolean
}

class FakeChangePasswordViewModel : ChangePasswordViewModel() {
    override var state: ChangePasswordFormState = ChangePasswordFormState()
    override val validationEventChannel = Channel<ValidationEvent>()
    override val message = MutableStateFlow("")
    override val validationEvents = emptyFlow<ValidationEvent>()
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun success(result: String) {}

    override fun failed(exception: Throwable?) {}

    override fun submitNewPassword() {}

    override fun onEvent(event: ChangePasswordFormEvent) {}

    override fun disconnectOtherDevices() {}

    override fun enableButton(): Boolean = true
}
