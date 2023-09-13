package com.soujunior.petjournal.ui.accountManager.changePasswordScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.ValidationEvent
import com.soujunior.petjournal.ui.states.TaskState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ChangePasswordViewModel : ViewModel(){
    abstract var state: ChangePasswordFormState
    abstract val validationEventChannel: Channel<ValidationEvent>
    abstract val success: LiveData<String>
    abstract val error: LiveData<String>
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