package com.soujunior.petjournal.ui.accountManager.registerScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class RegisterScreenViewModel : ViewModel() {
    abstract var state: RegisterFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()
    abstract fun success(resultPostRegister: String)
    abstract fun failed(exception: Throwable?)
    abstract fun submitData()
    abstract fun onEvent(event: RegisterFormEvent)
    abstract fun enableButton():Boolean
}