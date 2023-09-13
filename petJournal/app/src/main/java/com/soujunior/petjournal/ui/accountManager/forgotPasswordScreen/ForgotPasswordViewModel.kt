package com.soujunior.petjournal.ui.accountManager.forgotPasswordScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
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