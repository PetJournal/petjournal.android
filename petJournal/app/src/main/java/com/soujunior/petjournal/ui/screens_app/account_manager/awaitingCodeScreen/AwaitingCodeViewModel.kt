package com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen

import androidx.lifecycle.ViewModel
import com.soujunior.petjournal.ui.states.TaskState
import com.soujunior.petjournal.ui.util.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class AwaitingCodeViewModel : ViewModel() {
    abstract val state: StateFlow<com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormState>
    abstract val buttonIsEnable: StateFlow<Boolean>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()
    abstract val message: StateFlow<String>
    abstract val taskState: StateFlow<TaskState>
    abstract fun postOtpVerification()
    abstract fun failed(exception: Throwable?)
    abstract fun success(resultPostAwaitingCode: String)
    abstract fun onEvent(event: com.soujunior.petjournal.ui.screens_app.account_manager.awaitingCodeScreen.AwaitingCodeFormEvent)
    abstract fun enableButton(): Boolean
}