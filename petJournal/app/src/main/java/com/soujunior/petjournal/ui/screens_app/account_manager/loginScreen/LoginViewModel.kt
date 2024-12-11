package com.soujunior.petjournal.ui.screens_app.account_manager.loginScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeLoginViewModel : LoginViewModel() {
    override val message = MutableStateFlow("Mensagem de Teste")
    override var state: LoginFormState
        get() = LoginFormState()
        set(value) {}
    override val validationEventChannel = Channel<ValidationEvent>()
    override val validationEvents = emptyFlow<ValidationEvent>()
    override val taskState = MutableStateFlow<TaskState>(TaskState.Idle)
    override fun passwordRemember() {
    }

    override fun success(resultPostLogin: String) {
    }

    override fun failed(exception: Throwable?) { /* No-op */
    }

    override fun submitData() {
    }

    override fun enableButton(): Boolean {
        return true
    }

    override fun onEvent(event: LoginFormEvent) {
    }
}

abstract class LoginViewModel : ViewModel() {
    abstract var state: LoginFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()

    abstract val message: StateFlow<String>

    abstract val taskState: StateFlow<TaskState>

    abstract fun passwordRemember()
    abstract fun success(resultPostLogin: String)
    abstract fun failed(exception: Throwable?)
    abstract fun submitData()
    abstract fun enableButton(): Boolean
    abstract fun onEvent(event: LoginFormEvent)
}