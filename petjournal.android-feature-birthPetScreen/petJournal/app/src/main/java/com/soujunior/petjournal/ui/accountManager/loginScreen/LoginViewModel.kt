package com.soujunior.petjournal.ui.accountManager.loginScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import com.soujunior.petjournal.ui.util.ValidationEvent
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.flow.StateFlow

abstract class LoginViewModel : ViewModel() {
    abstract var state: LoginFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val message : StateFlow<String>

    abstract val taskState: StateFlow<TaskState>

    abstract fun passwordRemember()
    abstract fun success(resultPostLogin: String)
    abstract fun failed(exception: Throwable?)
    abstract fun submitData()
    abstract fun enableButton(): Boolean
    abstract fun onEvent(event: LoginFormEvent)
}