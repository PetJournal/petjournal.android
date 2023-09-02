package com.soujunior.petjournal.ui.accountManager.registerScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.mapper.User
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class RegisterViewModel : ViewModel() {
    abstract var state: RegisterFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>

    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract fun success(resultPostRegister: User)
    abstract fun failed(exception: Throwable?)
    abstract fun submitData()
    abstract fun onEvent(event: RegisterFormEvent)
    abstract fun enableButton(): Boolean
    abstract fun change(
        name: String? = null,
        lastName: String? = null,
        email: String? = null,
        phone: String? = null,
        password: String? = null,
        repeatedPassword: String? = null,
        privacy: Boolean? = null
    )

}