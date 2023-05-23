package com.soujunior.petjournal.ui.accountManager.loginScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import androidx.lifecycle.LiveData
import com.soujunior.domain.entities.auth.LoginModel

abstract class LoginScreenViewModel : ViewModel() {
    abstract var state: LoginFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()
    abstract fun passwordRemember()
    abstract fun success(resultPostLogin: String)
    abstract fun failed(exception: Throwable?)
    abstract fun submitData()
    abstract fun onEvent(event: LoginFormEvent)
}