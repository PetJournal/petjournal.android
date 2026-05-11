package com.soujunior.petjournal.ui.screensapp.accountmanager.registerScreen

import androidx.lifecycle.ViewModel
import com.soujunior.domain.model.User
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeRegisterViewModel : RegisterViewModel() {
    override var state: RegisterFormState = RegisterFormState()
    override val validationEventChannel = Channel<ValidationEvent>()
    override val message = MutableStateFlow("Mensagem de Teste")
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)

    override val validationEvents = emptyFlow<ValidationEvent>()

    override fun success(resultPostRegister: User) {
    }

    override fun failed(exception: Throwable?) {
    }

    override fun submitData() {
    }

    override fun onEvent(event: RegisterFormEvent) {
    }

    override fun enableButton(): Boolean {
        return true
    }

    override fun change(
        name: String?,
        lastName: String?,
        email: String?,
        phone: String?,
        password: String?,
        repeatedPassword: String?,
        privacy: Boolean?,
    ) {
    }
}

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
        privacy: Boolean? = null,
    )
}
