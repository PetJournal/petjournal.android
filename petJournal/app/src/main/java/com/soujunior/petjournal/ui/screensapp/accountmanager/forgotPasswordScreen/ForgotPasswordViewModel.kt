package com.soujunior.petjournal.ui.screensapp.accountmanager.forgotPasswordScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ForgotPasswordViewModel : ViewModel() {
    abstract var state: ForgotPasswordFormState
    abstract val validationEventChannel: Channel<ValidationEvent>

    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>

    abstract fun change(email: String?)

    abstract fun success(resultPostSubmit: String)

    abstract fun failed(exception: Throwable?)

    abstract fun submitData()

    abstract fun onEvent(event: ForgotPasswordFormEvent)

    abstract fun enableButton(): Boolean
}

class FakeForgotPasswordViewModel : ForgotPasswordViewModel() {
    override var state = ForgotPasswordFormState()
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()
    override val message = MutableStateFlow("")
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override fun change(email: String?) {}

    override fun success(resultPostSubmit: String) {}

    override fun failed(exception: Throwable?) {}

    override fun submitData() {}

    override fun onEvent(event: ForgotPasswordFormEvent) {}

    override fun enableButton(): Boolean = true
}
